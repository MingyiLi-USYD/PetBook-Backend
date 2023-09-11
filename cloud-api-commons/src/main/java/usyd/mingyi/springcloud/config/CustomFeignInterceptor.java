package usyd.mingyi.springcloud.config;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usyd.mingyi.springcloud.utils.BaseContext;

@Configuration
@Slf4j
public class CustomFeignInterceptor {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // 在这里可以修改请求
            template.header("X-UserId", String.valueOf(BaseContext.getCurrentId()));
            //log.info("现在的头信息: {}",BaseContext.getCurrentId().toString());
            template.header("X-Username", String.valueOf(BaseContext.getCurrentId()));
        };
    }

}
