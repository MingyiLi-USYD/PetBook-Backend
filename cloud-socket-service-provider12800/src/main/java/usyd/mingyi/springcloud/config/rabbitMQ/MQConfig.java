package usyd.mingyi.springcloud.config.rabbitMQ;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MQConfig {

    @Value("${serverId}")
    private String serverId;

    @Value("${queueName}")
    private String queueName;

    public static final String MESSAGE_EXCHANGE = "MESSAGE_EXCHANGE";

    @Bean(MESSAGE_EXCHANGE)
    public TopicExchange messageExchange(){
        return new TopicExchange(MESSAGE_EXCHANGE,true,false);
    }
    @Bean
    public Queue queueMessage() {
        return new Queue(queueName, true);
    }


    @Bean
    public Binding bindingMessageA(Queue queueMessage, @Qualifier(MESSAGE_EXCHANGE) TopicExchange messageExchange) {
        return BindingBuilder.bind(queueMessage).to(messageExchange).with("#");
    }




}


