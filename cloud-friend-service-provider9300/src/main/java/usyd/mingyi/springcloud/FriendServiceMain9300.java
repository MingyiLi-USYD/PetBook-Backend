package usyd.mingyi.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FriendServiceMain9300 {
    public static void main(String[] args) {
        SpringApplication.run(FriendServiceMain9300.class,args);
    }
}
