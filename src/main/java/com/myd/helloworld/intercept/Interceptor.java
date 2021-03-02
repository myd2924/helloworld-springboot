package com.myd.helloworld.intercept;
import java.lang.reflect.InvocationTargetException;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 09:31
 * @Description: aop拦截器
 */
public interface Interceptor {
    /**
     * 事前
     * @return
     */
    boolean before();

    /**
     * 事后
     */
    void after();

    /**
     * 取代原有事件方法
     * @param invocation 回调参数 可以通过它回调proceed 会调原有事件
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    Object around(Invocation invocation) throws InvocationTargetException,IllegalAccessException;

    /**
     * 是否返回方法 事件没有发生异常执行
     */
    void afterReturn();

    /**
     * 当发生异常 执行
     */
    void afterThrowing();

    /**
     * 是否使用around取代原方法
     * @return
     */
    boolean userAround();
}
