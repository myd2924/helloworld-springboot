package com.myd.helloworld.chapter10.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/30 17:46
 * @Description:
 */
public class Interceptor1 implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request ,
                             HttpServletResponse response , Object handler)
            throws Exception {
        System.out.println("啥也不干");
        return true;

    }

    @Override
    public void postHandle (HttpServletRequest request ,
                            HttpServletResponse response , Object handler,
                            ModelAndView modelAndView) throws Exception {
        System.out.println("处理后置方法");
    }

    @Override
    public void afterCompletion (HttpServletRequest request ,
                                 HttpServletResponse response , Object handler, Exception ex )
            throws Exception {
        System.out.println("处理完成方法");
    }
}
