package usyd.mingyi.springcloud.service;

import cn.hutool.core.lang.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import usyd.mingyi.springcloud.entity.ChatMessage;

import java.util.concurrent.atomic.AtomicInteger;


@Service
public class ChatServiceImp implements ChatService {
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;




    public void sendMsgToQueue(ChatMessage chatMessage){
/*        BoundHashOperations<String, String, Object> hashOperations
                = redisTemplate.boundHashOps("socketIOLocation");
        try {

            String correlationId = UUID.randomUUID().toString();
            String messageString = objectMapper.writeValueAsString(chatMessage);
            MessageProperties properties = new MessageProperties();
            properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            Message message = new Message(messageString.getBytes(), properties);
            properties.setReceivedExchange(MQConfig.MESSAGE_EXCHANGE);
            properties.setReceivedRoutingKey("#");
           // messages.put(correlationId,message);
            rabbitTemplate.send(MQConfig.MESSAGE_EXCHANGE, "#", message,new CorrelationData(correlationId));
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }*/

    }

}
