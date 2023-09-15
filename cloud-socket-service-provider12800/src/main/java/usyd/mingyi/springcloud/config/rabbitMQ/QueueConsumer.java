package usyd.mingyi.springcloud.config.rabbitMQ;

import com.corundumstudio.socketio.SocketIOClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import usyd.mingyi.springcloud.component.CacheManager;
import usyd.mingyi.springcloud.entity.*;
import usyd.mingyi.springcloud.pojo.User;
import usyd.mingyi.springcloud.service.UserServiceFeign;

import java.util.Map;

@Component
@Slf4j
public class QueueConsumer {
    @Qualifier("redisDecorator")
    @Autowired
    CacheManager clientCache;

    @Autowired
    UserServiceFeign userServiceFeign;

    @RabbitListener(queues = "${chatQueue}")
    public void receiveChatMessage(ChatMessage chatMessage) {
        User basicUserInfoById = userServiceFeign.getUserById(Long.valueOf(chatMessage.getFromId()));
        Map<String, SocketIOClient> chatServer = clientCache.getChatServer();
        ResponseMessage<ChatMessage> res = new ResponseMessage<>(1, chatMessage, basicUserInfoById);
        if (!chatServer.containsKey(chatMessage.getToId())) {
            log.info("not found in this server");
        } else {
            SocketIOClient userClient = chatServer.get(chatMessage.getToId());
            userClient.sendEvent("responseMessage", res);
        }
    }
    @RabbitListener(queues = "${serviceQueue}")
    public void receiveServiceMessage(ServiceMessage message) {
        log.info(message.toString());
    }

    @RabbitListener(queues = "${systemQueue}")
    public void receiveSystemMessage(SystemMessage message) {
        log.info(message.toString());
    }

}
