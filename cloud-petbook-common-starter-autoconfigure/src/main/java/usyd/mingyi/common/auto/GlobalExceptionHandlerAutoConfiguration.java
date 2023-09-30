package usyd.mingyi.common.auto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usyd.mingyi.common.service.GlobalExceptionHandler;

@Configuration
@ConditionalOnWebApplication
@Slf4j
public class GlobalExceptionHandlerAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(GlobalExceptionHandler.class)
    //可以考虑后面抽象个接口出来 这样才好做自动装配 现在这种自动装配等于没有自动装配
    //或者继承GlobalExceptionHandler 然后重写
    public GlobalExceptionHandler myControllerAdvice() {
        log.info("注册common中的全局异常处理器");
        return new GlobalExceptionHandler();
    }
}
