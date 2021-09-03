package com.myd.helloworld.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/9/3 09:46
 * @Description:
 *
 * 设置为注解@LogFilter1标记的方法，有标记的方法触发该AOP，没有标记就没有：@Pointcut(value = "@annotation(com.train.aop.annotation.LogFilter1)")
 * 采用表达式批量添加切入点 ：@Pointcut(value = "execution(public * com.train.aop.controller.AopController.*(..))")
 *
 * 多种方式可组合
 */
@Component
@Aspect
public class AopAnnotationDefine {

    private static final Logger logger = LoggerFactory.getLogger(AopAnnotationDefine.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 第一个* ：返回任何类型
     * 第二个*：任何类
     * 第三个*：任何方法
     * 两个点..：任何参数
     */
    @Pointcut(value = "execution(public * com.myd.helloworld.controller.*.*(..))")
    public void pointcut1(){}

    /**
     * 注解方式
     */
    @Pointcut(value = "@annotation(com.myd.helloworld.annotation.AnnotationAop)")
    public void pointcut2(){}

    @Before(value = "pointcut1()")
    public void before(){
        System.out.println("前置-pointcut1--批量定义方式");
    }

    @After(value = "pointcut2() || pointcut1()")
    public void after(JoinPoint joinPoint){
        logger.info("logger-----------------------------");
        System.out.println("后置-pointcut2-注解定义方式");
        final ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        final HttpServletRequest request = requestAttributes.getRequest();
        System.out.println("<==============================>");
        System.out.println("请求来源：=》"+request.getRemoteAddr());
        System.out.println("请求URL："+request.getRequestURL().toString());
        System.out.println("请求方式："+request.getMethod());
        System.out.println("响应方法："+joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
        System.out.println("请求参数："+ Arrays.toString(joinPoint.getArgs()));
        System.out.println("<------------------------------>");
        startTime.set(System.currentTimeMillis());
        logger.info(startTime.get().toString());

    }

}
