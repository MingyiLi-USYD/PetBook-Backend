package usyd.mingyi.common.service;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import usyd.mingyi.common.bean.FeignConfigProperties;
import usyd.mingyi.common.utils.BaseContext;

public class FeignInterceptor implements RequestInterceptor {
    @Autowired
    FeignConfigProperties feignConfigProperties;


    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(feignConfigProperties.getUserIdKeyName(), String.valueOf(BaseContext.getCurrentId()));
        requestTemplate.header(feignConfigProperties.getUsernameKeyName(), String.valueOf(BaseContext.getCurrentId()));
    }
}
