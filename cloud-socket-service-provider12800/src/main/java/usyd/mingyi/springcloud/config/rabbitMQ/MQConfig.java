package usyd.mingyi.springcloud.config.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MQConfig {

    @Value("${serverId}")
    private String serverId;

    @Value("${chatQueue}")
    private String chatQueue;

    @Value("${serviceQueue}")
    private String serviceQueue;

    @Value("${systemQueue}")
    private String systemQueue;

    public static final String CHAT_EXCHANGE = "CHAT_EXCHANGE";
    public static final String SERVICE_EXCHANGE = "SERVICE_EXCHANGE";
    public static final String SYSTEM_EXCHANGE = "SYSTEM_EXCHANGE";

    @Bean
    public DirectExchange chatExchange(){
        return new DirectExchange(CHAT_EXCHANGE,true,false);
    }
    @Bean
    public Queue chatQueue() {
        return new Queue(chatQueue, true);
    }


    @Bean
    public Binding bindingChat(Queue chatQueue, DirectExchange chatExchange) {
        return BindingBuilder.bind(chatQueue).to(chatExchange).with(serverId);
    }

    @Bean()
    public DirectExchange serviceExchange(){
        return new DirectExchange(SERVICE_EXCHANGE,true,false);
    }
    @Bean
    public Queue serviceQueue() {
        return new Queue(serviceQueue, true);
    }


    @Bean
    public Binding bindingService(Queue serviceQueue, DirectExchange serviceExchange) {
        return BindingBuilder.bind(serviceQueue).to(serviceExchange).with(serverId);
    }


    @Bean()
    public DirectExchange systemExchange(){
        return new DirectExchange(SYSTEM_EXCHANGE,true,false);
    }
    @Bean
    public Queue systemQueue() {
        return new Queue(systemQueue, true);
    }


    @Bean
    public Binding bindingSystem(Queue systemQueue, DirectExchange systemExchange) {
        return BindingBuilder.bind(systemQueue).to(systemExchange).with(serverId);
    }




}


