package usyd.mingyi.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FriendServiceMain12400 {
    public static void main(String[] args) {
        SpringApplication.run(FriendServiceMain12400.class,args);
    }
}
