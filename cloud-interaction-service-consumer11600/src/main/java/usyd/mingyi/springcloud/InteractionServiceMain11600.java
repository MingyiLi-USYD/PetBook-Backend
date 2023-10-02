package usyd.mingyi.springcloud;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients(basePackages = "usyd.mingyi.common")
@EntityScan(basePackages = "com.rubin.shardingjdbcdemo.entity")
@EnableTransactionManagement
public class InteractionServiceMain11600 {
    public static void main(String[] args) {
        SpringApplication.run(InteractionServiceMain11600.class, args);
    }
}
