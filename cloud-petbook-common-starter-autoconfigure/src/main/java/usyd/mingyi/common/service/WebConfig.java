package usyd.mingyi.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import usyd.mingyi.common.bean.WebConfigProperties;


public class WebConfig implements WebMvcConfigurer {
    @Autowired
    HandlerInterceptor myInterceptor;
    @Autowired
    WebConfigProperties webConfigProperties;



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(webConfigProperties.getExcludes());//放行模式;
    }
}
