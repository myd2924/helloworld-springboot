package com.myd.helloworld.intercept;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 09:49
 * @Description: 自定义的拦截器
 */
public class MyInterceptor implements Interceptor {
    @Override
    public boolean before() {
        System.out.println("before....");
        return true;
    }

    @Override
    public void after() {
        System.out.println("after....");
    }

    @Override
    public Object around(Invocation invocation) throws InvocationTargetException, IllegalAccessException {
        System.out.println("around before....");
        Object obj = invocation.proceed();
        System.out.println("around after....");
        return obj;
    }

    @Override
    public void afterReturn() {
        System.out.println("afterReturning ....");
    }

    @Override
    public void afterThrowing() {
        System.out.println("afterThrowing ....");
    }

    @Override
    public boolean userAround() {
        return true;
    }
}
