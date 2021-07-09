package com.myd.helloworld.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/9 10:02
 * @Description:  aop 失效测试及解决
 */
@Component
@Aspect
public class HelloAspectInvalidation {

    @Pointcut("execution(* com.myd.helloworld.aop.HelloServiceImpl.sayHello(..))")
    public void invalid(){}

    @Before(value = "invalid()")
    public void before(){
        System.out.println("***************前置增强");
    }
    @After(value = "invalid()")
    public void after(){
        System.out.println("后置增强**************");
    }

}
