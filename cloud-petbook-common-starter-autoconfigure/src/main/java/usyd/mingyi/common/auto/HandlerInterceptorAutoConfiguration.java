package usyd.mingyi.common.auto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import usyd.mingyi.common.service.MyHandlerInterceptor;


@Configuration
@ConditionalOnMissingBean(HandlerInterceptor.class)
@Slf4j
public class HandlerInterceptorAutoConfiguration {
    @Bean
    public HandlerInterceptor handlerInterceptor() {
        log.info("注册common自定义拦截器");
        return new MyHandlerInterceptor();
    }
}
