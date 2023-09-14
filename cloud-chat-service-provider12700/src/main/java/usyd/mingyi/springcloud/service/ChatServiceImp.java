package usyd.mingyi.springcloud.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class ChatServiceImp implements ChatService {
/*    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private  RabbitTemplate rabbitTemplate;


    public void sendMsgToQueue(ChatMessage chatMessage){

        try {
            String correlationId = UUID.randomUUID().toString();
            String messageString = objectMapper.writeValueAsString(chatMessage);
            MessageProperties properties = new MessageProperties();
            properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            properties.setHeader(Max_Retry,new AtomicInteger(3));
            Message message = new Message(messageString.getBytes(), properties);
            properties.setReceivedExchange(MQConfig.MESSAGE_EXCHANGE);
            properties.setReceivedRoutingKey("#");
            messages.put(correlationId,message);
            rabbitTemplate.send(MQConfig.MESSAGE_EXCHANGE, "#", message,new CorrelationData(correlationId));
        }catch (Exception e){
            throw new CustomException(e.getMessage());
        }

    }*/

}
