package usyd.mingyi.springcloud.config.rabbitMQ;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import usyd.mingyi.common.entity.ChatMessage;
import usyd.mingyi.common.entity.ResponseMessage;
import usyd.mingyi.common.entity.ServiceMessage;
import usyd.mingyi.common.entity.SystemMessage;
import usyd.mingyi.common.feign.FriendServiceFeign;
import usyd.mingyi.common.feign.UserServiceFeign;
import usyd.mingyi.common.pojo.User;
import usyd.mingyi.common.pojo.Friendship;
import usyd.mingyi.common.utils.BaseContext;
import usyd.mingyi.springcloud.component.CacheManager;
import java.util.Map;
import java.util.stream.Stream;

import static usyd.mingyi.common.entity.ServiceMessageType.FRIEND_OFFLINE;
import static usyd.mingyi.common.entity.ServiceMessageType.FRIEND_ONLINE;

@Component
@Slf4j
public class QueueConsumer {
    @Qualifier("redisDecorator")
    @Autowired
    CacheManager clientCache;

    @Autowired
    UserServiceFeign userServiceFeign;
    @Autowired
    FriendServiceFeign friendServiceFeign;

    @RabbitListener(queues = "${chatQueue}")
    public void receiveChatMessage(ChatMessage chatMessage) {
        if (chatMessage == null || chatMessage.getFromId() == null) {
            //记录日志
            return;
        }

        BaseContext.setCurrentId(Long.valueOf(chatMessage.getFromId()));
        User basicUserInfoById = userServiceFeign.getUserById(Long.valueOf(chatMessage.getFromId()));
        Map<String, SocketIOClient> chatServer = clientCache.getChatServer();
        ResponseMessage<ChatMessage> res = new ResponseMessage<>(1, chatMessage, basicUserInfoById);
        if (!chatServer.containsKey(chatMessage.getToId())) {
            log.info("not found in this server");
        } else {
            SocketIOClient userClient = chatServer.get(chatMessage.getToId());
            userClient.sendEvent("responseMessage", res);
        }
        BaseContext.clearCurrentId();
    }

    @RabbitListener(queues = "${serviceQueue}")
    public void receiveServiceMessage(ServiceMessage serviceMessage) {
        BaseContext.setCurrentId(Long.valueOf(serviceMessage.getFromId()));
        if (serviceMessage.getType() == FRIEND_ONLINE ||
                serviceMessage.getType() == FRIEND_OFFLINE) {
            syncOnAndOffToClient(serviceMessage);
        } else {
            syncFriendOperationToClient(serviceMessage);
        }
        BaseContext.clearCurrentId();
    }

    @RabbitListener(queues = "${systemQueue}")
    public void receiveSystemMessage(SystemMessage message) {
        log.info(message.toString());
    }


    public void syncOnAndOffToClient(ServiceMessage serviceMessage) {
        User basicUserInfoById = userServiceFeign.getUserById(Long.valueOf(serviceMessage.getFromId()));
        Map<String, SocketIOClient> chatServer = clientCache.getChatServer();

        //找到所有好友
        Stream<Long> allFriends = friendServiceFeign.getFriendshipList().stream().map(Friendship::getFriendId);

        ResponseMessage<ServiceMessage> res = new ResponseMessage<>(2, serviceMessage, basicUserInfoById);
        allFriends.forEach(friend -> {
            String friendId = String.valueOf(friend);
            if (chatServer.containsKey(friendId)) {
                SocketIOClient userClient = chatServer.get(friendId);
                res.getMessage().setToId(String.valueOf(friend));
                userClient.sendEvent("friendEvent", res);
            }

        });
    }

    public void syncFriendOperationToClient(ServiceMessage serviceMessage) {
        User user = userServiceFeign.getUserById(Long.valueOf(serviceMessage.getFromId()));
        if (user == null) {
            return;
        }
        socketSendMsg(serviceMessage, user);
    }

    public void socketSendMsg(ServiceMessage serviceMessage, User user) {
        Map<String, SocketIOClient> chatServer = clientCache.getChatServer();
        ResponseMessage<ServiceMessage> res = new ResponseMessage<>(2, serviceMessage, user);
        if (chatServer.containsKey(serviceMessage.getToId())) {
            SocketIOClient userClient = chatServer.get(serviceMessage.getToId());
            userClient.sendEvent("friendEvent", res);
        }
    }

}
