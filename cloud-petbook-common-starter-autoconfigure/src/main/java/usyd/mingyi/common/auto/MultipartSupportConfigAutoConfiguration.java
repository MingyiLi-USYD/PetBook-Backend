package usyd.mingyi.common.auto;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class MultipartSupportConfigAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(Encoder.class)
    public Encoder feignEncoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        log.info("注册common中的feign encoder");
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }
}
