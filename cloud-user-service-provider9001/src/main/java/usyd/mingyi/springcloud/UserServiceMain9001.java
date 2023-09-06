package usyd.mingyi.springcloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceMain9001.class,args);

    }

}
