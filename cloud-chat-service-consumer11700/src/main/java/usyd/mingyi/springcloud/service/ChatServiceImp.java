package usyd.mingyi.springcloud.service;

import cn.hutool.core.lang.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.entity.ChatMessage;

import java.util.concurrent.atomic.AtomicInteger;


@Service
public class ChatServiceImp implements ChatService {
/*
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    public static final String MESSAGE_EXCHANGE = "MESSAGE_EXCHANGE";

    public void sendMsgToQueue(ChatMessage chatMessage){
        BoundHashOperations<String, String, Object> hashOperations
                = redisTemplate.boundHashOps("socketIOLocation");
        String toId = chatMessage.getToId();
        if(!hashOperations.hasKey(toId)){
            return;
            //证明不在线 不需要同步socket
        }
        String serverId = (String) hashOperations.get(toId); //同时 serverId也是routingKey

        rabbitTemplate.convertAndSend(MESSAGE_EXCHANGE, serverId, chatMessage);
    }
*/

}
