package usyd.mingyi.springcloud.config.rest;

import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import usyd.mingyi.springcloud.common.CustomException;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.utils.JsonUtil;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class FeignResultDecoder implements Decoder {


    private final SpringDecoder decoder;

    public FeignResultDecoder(SpringDecoder decoder) {
        this.decoder = decoder;
    }
    @Override
    public Object decode(Response response, Type type) throws IOException, FeignException {
        Method method = response.request().requestTemplate().methodMetadata().method();
        //如果Feign接口的返回值不是 R{code:0,...} 结构类型，并且远程响应又是这个结构
        boolean notTheSame = method.getReturnType() != R.class;
        if (notTheSame) {
            //构造一个这个结构类型
            Type newType =
                    new ParameterizedType() {
                        @Override
                        public Type[] getActualTypeArguments() {
                            return new Type[]{type};
                        }
                        @Override
                        public Type getRawType() {
                            return R.class;
                        }
                        @Override
                        public Type getOwnerType() {
                            return null;
                        }
                    };
           R<?> result = (R) this.decoder.decode(response, newType);
             if(result.getCode().equals(0)){
                 throw new CustomException(result.getMsg());
             }
            //只返回data
            return result.getData();
        }
        R r = (R) this.decoder.decode(response, type);
        return r;
    }

}
