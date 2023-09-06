package usyd.mingyi.springcloud.config;
import feign.InvocationContext;
import feign.RequestInterceptor;
import feign.ResponseInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usyd.mingyi.springcloud.utils.BaseContext;

import java.io.IOException;

@Configuration
public class CustomFeignInterceptor {
    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // 在这里可以修改请求
            // 例如，你可以添加请求头或修改请求参数
            template.header("X-UserId", String.valueOf(BaseContext.getCurrentId()));

        };
    }
/*    @Bean
    ResponseInterceptor responseInterceptor(){

        return new ResponseInterceptor() {
            @Override
            public Object aroundDecode(InvocationContext invocationContext) throws IOException {
                return null;
            }
        };
    }*/

}
