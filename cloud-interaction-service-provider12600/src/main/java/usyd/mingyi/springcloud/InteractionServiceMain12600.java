package usyd.mingyi.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class InteractionServiceMain12600 {
    public static void main(String[] args) {
        SpringApplication.run(InteractionServiceMain12600.class,args);
    }
}
