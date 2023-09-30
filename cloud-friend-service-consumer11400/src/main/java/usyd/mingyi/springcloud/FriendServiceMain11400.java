package usyd.mingyi.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "usyd.mingyi.common")
public class FriendServiceMain11400 {
    public static void main(String[] args) {
        SpringApplication.run(FriendServiceMain11400.class,args);
    }
}
