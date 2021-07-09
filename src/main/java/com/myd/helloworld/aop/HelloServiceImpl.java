package com.myd.helloworld.aop;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 09:28
 * @Description:
 */
@Service
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        if(null == name || "".equals(name.trim())){
            throw new RuntimeException("parameter is null !");
        }
        System.out.println("hello： "+name);
    }

    @Override
    public void invalidAopSayHello(String name){
        sayHello(name);
    }

    @Autowired
    private HelloService helloService;
    /**
     * 自我注入
     * @param name
     */
    @Override
    public void oneAopSayHello(String name) {
        helloService.sayHello(name);
    }

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 上下文获取 原理同一 自我注入
     * @param name
     */
    @Override
    public void twoAopSayHello(String name) {
        applicationContext.getBean(HelloService.class).sayHello(name);
    }

    /**
     * AopContext 获取代理
     * @param name
     */
    @Override
    public void threeAopSayHello(String name) {
        final HelloService hellService = (HelloService)AopContext.currentProxy();
        hellService.sayHello(name);
    }

}
