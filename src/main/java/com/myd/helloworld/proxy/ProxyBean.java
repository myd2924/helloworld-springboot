package com.myd.helloworld.proxy;

import com.myd.helloworld.intercept.Interceptor;
import com.myd.helloworld.intercept.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 10:08
 * @Description:
 */
public class ProxyBean implements InvocationHandler {

    private Object target;

    private Interceptor interceptor;

    /**
     * 绑定代理对象
     * @param target 被代理对象
     * @param interceptor 拦截器
     * @return 代理对象
     */
    public static Object getProxyBean(Object target, Interceptor interceptor){
        ProxyBean proxyBean = new ProxyBean();
        //保存被代理对象
        proxyBean.target = target;
        //保存拦截器
        proxyBean.interceptor = interceptor;
        //生成代理对象
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), proxyBean);
        //返回被代理对象
        return proxy;

    }

    /**
     * 处理代理对象方法逻辑
     * @param proxy 代理对象
     * @param method 当前方法
     * @param args 运行参数
     * @return 方法调用结果
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        boolean exceptionFlag = false;
        Invocation invocation = new Invocation(args, method, proxy);
        Object retObj = null;
        try{
            if(this.interceptor.before()){
                retObj = this.interceptor.around(invocation);
            } else {
                retObj = method.invoke(proxy,args);
            }
        } catch (Exception e){
            exceptionFlag = true;
        }
        this.interceptor.after();
        if(exceptionFlag){
            this.interceptor.afterThrowing();
        } else {
            this.interceptor.afterReturn();
            return retObj;
        }
        return null;
    }
}