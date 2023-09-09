package usyd.mingyi.springcloud.config;
import feign.InvocationContext;
import feign.Response;
import feign.ResponseInterceptor;
import java.io.IOException;


public class MyResponseInterceptor implements ResponseInterceptor {


    @Override
    public Object aroundDecode(InvocationContext invocationContext) throws IOException {

        Response response = invocationContext.response();
        if (response.status()!=200) {

        }else {

        }

        return invocationContext.proceed();
    }
}
