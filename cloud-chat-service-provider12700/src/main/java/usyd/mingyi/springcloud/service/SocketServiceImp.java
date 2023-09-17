package usyd.mingyi.springcloud.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.entity.*;
import usyd.mingyi.springcloud.pojo.Friendship;

import java.util.List;

@Service
public class SocketServiceImp implements SocketService {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    FriendServiceFeign friendServiceFeign;


    public static final String CHAT_EXCHANGE = "CHAT_EXCHANGE";
    public static final String SERVICE_EXCHANGE = "SERVICE_EXCHANGE";
    public static final String SYSTEM_EXCHANGE = "SYSTEM_EXCHANGE";

    public void asyncChatMessageToClient(ChatMessage chatMessage) {
        BoundHashOperations<String, String, Object> hashOperations
                = redisTemplate.boundHashOps("socketIOLocation");
        String toId = chatMessage.getToId();
        if (!hashOperations.hasKey(toId)) {
            return;
            //证明不在线 不需要同步socket
        }
        //用户下线的时候 同时也会移除redis中的记录 可能会出现判断的时候存在 取的时候不在了
        // 需要在外面接住异常 或再次判断 是否为空
        String serverId = (String) hashOperations.get(toId); //同时 serverId也是routingKey

        rabbitTemplate.convertAndSend(CHAT_EXCHANGE, serverId, chatMessage);
    }


    public void asyncServiceMessageToClient(ServiceMessage serviceMessage) {
        BoundHashOperations<String, String, Object> hashOperations
                = redisTemplate.boundHashOps("socketIOLocation");
        if (serviceMessage.getType().equals(ServiceMessageType.FRIEND_ONLINE) ||
                serviceMessage.getType().equals(ServiceMessageType.FRIEND_OFFLINE)) {
            List<Friendship> friendshipList = friendServiceFeign.getFriendshipList();
            friendshipList.forEach(friendship -> {
                Long friendId = friendship.getFriendId();
                if(hashOperations.hasKey(friendId)){
                    String serverId = (String) hashOperations.get(friendship.getFriendId());
                    serviceMessage.setToId(String.valueOf(friendId));
                    rabbitTemplate.convertAndSend(SERVICE_EXCHANGE, serverId,serviceMessage);
                }
            });

        } else {
            String toId = serviceMessage.getToId();
            if (!hashOperations.hasKey(toId)) {
                return;
                //证明不在线 不需要同步socket
            }
            String serverId = (String) hashOperations.get(toId); //同时 serverId也是routingKey

            rabbitTemplate.convertAndSend(CHAT_EXCHANGE, serverId, serviceMessage);
        }

    }

    @Override
    public void asyncSystemMessageToClient(SystemMessage systemMessage) {
/*        if (systemMessage.getType().equals(SystemMessageType.FRIEND_OFFLINE)
            ||systemMessage.equals(SystemMessageType.FRIEND_OFFLINE)) {
            rabbitTemplate.convertAndSend(SYSTEM_EXCHANGE,"*",systemMessage);
        }*/
    }


}
