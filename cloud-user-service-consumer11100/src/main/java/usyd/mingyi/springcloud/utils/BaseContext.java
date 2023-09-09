package usyd.mingyi.springcloud.utils;

import com.alibaba.ttl.TransmittableThreadLocal;

public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new TransmittableThreadLocal<>();
   // private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }


    public static void clearCurrentId(){
        threadLocal.remove();
    }
}
