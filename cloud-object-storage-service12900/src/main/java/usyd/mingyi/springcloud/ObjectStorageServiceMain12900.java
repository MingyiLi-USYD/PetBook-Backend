package usyd.mingyi.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ObjectStorageServiceMain12900 {
    public static void main(String[] args) {
        SpringApplication.run(ObjectStorageServiceMain12900.class,args);
    }
}
