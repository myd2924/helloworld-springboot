package com.myd.helloworld.intercept;

import com.google.common.util.concurrent.RateLimiter;
import com.myd.helloworld.annotation.RequestLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/12/6 17:58
 * @Description: Guava rateLimiter实现
 */
@Component
@Slf4j
public class RequestLimiterInterceptor implements HandlerInterceptor {

    private final Map<String,RateLimiter> rateLimiterMap = new ConcurrentHashMap<>();

     @Override
     /**
      * preHandle在目标handler方法执行之前
      * 返回值 boolean类型  true表示放行该请求、false表示拦截
      * 作用：做权限验证、日志
      *
      */
     public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
         if(handler instanceof HandlerMethod){
             HandlerMethod handlerMethod = (HandlerMethod)handler;
             RequestLimiter requestLimiter = handlerMethod.getMethodAnnotation(RequestLimiter.class);
             //判断是否有注解
             if(requestLimiter != null){
                 final String requestURI = request.getRequestURI();
                 RateLimiter rateLimiter;
                 // 判断map集合中是否有创建好的令牌桶
                 if(!rateLimiterMap.containsKey(requestURI)){
                     //创建令牌桶,以n r/s往桶中放入令牌
                     final RateLimiter limiter = RateLimiter.create(requestLimiter.QPS());
                     rateLimiterMap.put(requestURI,limiter);
                 }
                 rateLimiter = rateLimiterMap.get(requestURI);
                 //获取令牌
                 final boolean acquire = rateLimiter.tryAcquire(requestLimiter.timeout(), requestLimiter.timeunit());
                 if(acquire){
                     log.info("令牌桶可以进来嘛");
                     return true;
                 } else {
                     log.info("令牌桶没进来嘛");
                     return false;
                 }
             }
         }
         return true;
    }
      @Override
      /**
       * postHandler在目标handler方法执行之后执行，在 视图渲染（render）之前执行
       * 视图渲染：就是把值填充到页面中的这个过程
       * 作用：可以在数据到达页面之前对域中的数据进行修改
       */
     public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

     }
     @Override
    /**
     * 在目标handler执行之后，而且在视图渲染执行之后执行。
     * 作用：做资源释放
     */
     public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
