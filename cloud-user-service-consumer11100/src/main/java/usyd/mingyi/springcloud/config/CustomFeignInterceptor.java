package usyd.mingyi.springcloud.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usyd.mingyi.springcloud.utils.BaseContext;

@Configuration
public class CustomFeignInterceptor {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // 在这里可以修改请求
            // 例如，你可以添加请求头或修改请求参数
            template.header("X-UserId", String.valueOf(BaseContext.getCurrentId()));
            template.header("X-Username", String.valueOf(BaseContext.getCurrentId()));
        };
    }

}
