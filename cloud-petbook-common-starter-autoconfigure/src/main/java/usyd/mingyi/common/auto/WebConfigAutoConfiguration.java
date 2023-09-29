package usyd.mingyi.common.auto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import usyd.mingyi.common.bean.WebConfigProperties;
import usyd.mingyi.common.service.WebConfig;

@Configuration
@ComponentScan(basePackages = "usyd.mingyi.common")
@EnableConfigurationProperties(value = {WebConfigProperties.class})
@ConditionalOnProperty(name = "myapp.webconfig.enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
public class WebConfigAutoConfiguration {

    @Bean
    @ConditionalOnBean(HandlerInterceptor.class)
    public WebMvcConfigurer webMvcConfigurer() {
        log.info("注册common自定义mvc");
        return new WebConfig();
    }


}
