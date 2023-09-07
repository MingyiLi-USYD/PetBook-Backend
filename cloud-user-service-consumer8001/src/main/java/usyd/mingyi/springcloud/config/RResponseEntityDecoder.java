/*
package usyd.mingyi.springcloud.config;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.json.JSONUtil;
import feign.FeignException;
import feign.Response;
import feign.codec.Decoder;
import usyd.mingyi.springcloud.common.R;
import usyd.mingyi.springcloud.pojo.User;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;


public class RResponseEntityDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws FeignException, IOException {

        InputStream inputStream = response.body().asInputStream();
             String responseBody = IoUtil.read(inputStream, "UTF-8");

            // 使用 TypeResolver 解析泛型类型
            TypeReference<R<?>> rTypeReference = new TypeReference<R<?>>() {
                @Override
                public Type getType() {
                    return type;
                }
            };

            R<?> bean = JSONUtil.toBean(responseBody, rTypeReference, true);


        return  new User();
    }



}
*/
