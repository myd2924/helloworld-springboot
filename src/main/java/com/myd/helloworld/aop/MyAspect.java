package com.myd.helloworld.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 11:36
 * @Description:
 */
@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(* com.myd.helloworld.service.User2ServiceImpl.printUser(..))")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(){
        System.out.println("切面前执行");
    }

    @After("pointCut()")
    public void after(){
        System.out.println("后置执行");
    }

    @AfterReturning("pointCut()")
    public void afterReturning(){
        System.out.println("返回后执行");
    }

    @AfterThrowing("pointCut()")
    public void afterThrowing(){
        System.out.println("异常时执行");
    }

    @Around("pointCut()")
    public void around(ProceedingJoinPoint jp) throws Throwable {
        System.out.println("环绕前");
        jp.proceed();
        System.out.println("环绕后");
    }

}
