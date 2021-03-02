package com.myd.helloworld.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 11:36
 * @Description:
 */
@Aspect
@Component
@Order(1)
public class MyAspect2 {

    @Pointcut("execution(* com.myd.helloworld.service.User2ServiceImpl.manyAspect(..))")
    public void manyAspect(){}

    @Before("manyAspect()")
    public void before(){
        System.out.println("切面前执行2");
    }

    @After("manyAspect()")
    public void after(){
        System.out.println("后置执行2");
    }

    @AfterReturning("manyAspect()")
    public void afterReturning(){
        System.out.println("返回后执行2");
    }

    @AfterThrowing("manyAspect()")
    public void afterThrowing(){
        System.out.println("异常时执行2");
    }

    @Around("manyAspect()")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("环绕前2");
        jp.proceed();
        System.out.println("环绕后2");
    }

}
