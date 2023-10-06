package usyd.mingyi.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import usyd.mingyi.common.utils.BaseContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class MyHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前拦截，提取请求头信息
        String username = request.getHeader("X-Username");
        String userId = request.getHeader("X-UserId");
        if (username == null || userId == null) {
            //处理错误
            //log.info("no userId found");
        }
        if(userId!=null){
            BaseContext.setCurrentId(Long.valueOf(userId));
        }
        // 在这里可以使用 authorizationHeader 做进一步处理
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContext.clearCurrentId();
    }
}
