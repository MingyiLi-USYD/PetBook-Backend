package usyd.mingyi.springcloud.config.rabbitMQ;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import usyd.mingyi.springcloud.component.CacheManager;

@Component
@Slf4j
public class QueueConsumer {
    @Value("${queueName}")
    private String queueName;

/*
    @Autowired
    @Qualifier("redisDecorator")
    private CacheManager clientCache;
    @Autowired
    private ObjectMapper objectMapper;
*/



    @RabbitListener(queues = "${queueName}")
    public void receiveChatMessage(Message message) {
        System.out.println(message);
    }


}
