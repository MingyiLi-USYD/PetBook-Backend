package usyd.mingyi.common.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import usyd.mingyi.common.entity.RequestMessage;

@Aspect
@Slf4j
//需要自动装配
public class FeignClientAspect {
    @Around("execution(* usyd.mingyi.common.feign.ChatServiceFeign.*(..))")
    public Object aroundFeignMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args!=null&&args.length>0&&args[0] instanceof RequestMessage) {
            RequestMessage requestMessage = (RequestMessage) args[0];
            if (requestMessage.getToId().equals(requestMessage.getFromId())){
                log.info("发给自己的 取消执行");
                 return null;
            }
        }
        Object result = joinPoint.proceed();
        return result;
    }

}
