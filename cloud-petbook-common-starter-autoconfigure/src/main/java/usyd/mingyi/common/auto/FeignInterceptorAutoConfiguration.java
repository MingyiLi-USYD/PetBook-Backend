package usyd.mingyi.common.auto;

import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import usyd.mingyi.common.aop.FeignClientAspect;
import usyd.mingyi.common.bean.FeignConfigProperties;
import usyd.mingyi.common.service.FeignInterceptor;

@EnableConfigurationProperties(FeignConfigProperties.class)
@Slf4j
@Configuration
public class FeignInterceptorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(FeignInterceptor.class)
    RequestInterceptor requestInterceptor(){

        log.info("注册common中的feign拦截器");
        return new FeignInterceptor();
    }
    @Bean
    FeignClientAspect feignClientAspect(){
        log.info("注册feign AOP");
        return new FeignClientAspect();
    }


}
