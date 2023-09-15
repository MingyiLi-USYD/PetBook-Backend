package usyd.mingyi.springcloud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.entity.ChatMessage;
import usyd.mingyi.springcloud.entity.RequestMessage;
import usyd.mingyi.springcloud.entity.ServiceMessage;

@Service
public class SocketServiceImp implements SocketService{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    public static final String CHAT_EXCHANGE = "CHAT_EXCHANGE";
    public static final String SERVICE_EXCHANGE = "SERVICE_EXCHANGE";
    public static final String SYSTEM_EXCHANGE = "SYSTEM_EXCHANGE";

    public void asyncChatMessageToClient(ChatMessage chatMessage){
        BoundHashOperations<String, String, Object> hashOperations
                = redisTemplate.boundHashOps("socketIOLocation");
        String toId = chatMessage.getToId();
        if(!hashOperations.hasKey(toId)){
            return;
            //证明不在线 不需要同步socket
        }
        String serverId = (String) hashOperations.get(toId); //同时 serverId也是routingKey

        rabbitTemplate.convertAndSend(CHAT_EXCHANGE, serverId, chatMessage);
    }

/*
    public void remindFriends(ServiceMessage serviceMessage) {

        BoundHashOperations<String, String, Object> hashOperations
                = redisTemplate.boundHashOps("socketIOLocation");
        String toId = serviceMessage.getToId();
        if(!hashOperations.hasKey(toId)){
            return;
            //证明不在线 不需要同步socket
        }
        String serverId = (String) hashOperations.get(toId); //同时 serverId也是routingKey

        rabbitTemplate.convertAndSend(SERVICE_EXCHANGE, serverId, serviceMessage);
    }*/
}
