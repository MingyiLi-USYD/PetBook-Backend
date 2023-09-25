package usyd.mingyi.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;



@SpringBootApplication
@EnableDiscoveryClient
public class UserAuthenticationMain7000 {
    public static void main(String[] args) {
        SpringApplication.run(UserAuthenticationMain7000.class,args);
    }
}
