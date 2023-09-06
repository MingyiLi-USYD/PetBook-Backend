package usyd.mingyi.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAuthorizationServer
@EnableFeignClients
public class UserAuthenticationMain7000 {
    public static void main(String[] args) {
        SpringApplication.run(UserAuthenticationMain7000.class,args);
    }
}
