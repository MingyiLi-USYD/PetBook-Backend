package usyd.mingyi.springcloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceMain12100 {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceMain12100.class,args);

    }

}
