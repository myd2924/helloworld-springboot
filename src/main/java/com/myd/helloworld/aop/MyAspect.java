package com.myd.helloworld.aop;

import com.myd.helloworld.annotation.AopLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

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
        System.out.println("***************环绕前*********************");
        //1、获取切入点的目标对象
        final Object target = jp.getTarget();
        System.out.println(target.getClass().getName());

        //2、方法名称等
        final Signature signature = jp.getSignature();
        System.out.println(signature.getName());

        //3、方法上的注解信息
        MethodSignature methodSignature = (MethodSignature)signature;
        final Method method = methodSignature.getMethod();
        if(Objects.nonNull(method)){
            final AopLog aopLog = method.getAnnotation(AopLog.class);
            System.out.println("aopLog的注解信息："+aopLog.title()+" -"+aopLog.logService()+" -"+aopLog.isSaveRequestData());
        }

        //4、获取参数列表
        final Object[] jpArgs = jp.getArgs();
        for(Object o:jpArgs){
            System.out.println("切入方法的参数："+o);
        }

        jp.proceed();
        System.out.println("***************环绕后*********************");
    }

}
