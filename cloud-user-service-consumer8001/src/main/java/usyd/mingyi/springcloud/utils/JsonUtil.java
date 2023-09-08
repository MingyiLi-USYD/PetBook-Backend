package usyd.mingyi.springcloud.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.lang.reflect.Type;

public class JsonUtil {
    public static <T> T json2obj(String jsonStr, Type targetType) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JavaType javaType = TypeFactory.defaultInstance().constructType(targetType);
            return mapper.readValue(jsonStr, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException("将JSON转换为对象时发生错误:" + jsonStr, e);
        }
    }

}
