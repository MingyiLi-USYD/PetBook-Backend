package usyd.mingyi.springcloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class UserServiceMain11100 {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceMain11100.class,args);

    }

}
