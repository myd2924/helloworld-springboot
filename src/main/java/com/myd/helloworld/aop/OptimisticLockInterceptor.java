package com.myd.helloworld.aop;

import com.myd.helloworld.annotation.RetryOnFailure;
import com.myd.helloworld.except.TryAgainException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/9/13 21:18
 * @Description: 解决乐观锁问题
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class OptimisticLockInterceptor {

    //private ThreadLocal<Integer> local = new ThreadLocal();

    @Pointcut(value = "@annotation(com.myd.helloworld.annotation.RetryOnFailure)")
    public void pointCut(){}

    @Around(value = "pointCut()")
    @Transactional(rollbackFor = TryAgainException.class)
    public Object operate(ProceedingJoinPoint pjp) throws Throwable {
        // 获取拦截的方法名
        MethodSignature msig = (MethodSignature) pjp.getSignature();
        // 返回被织入增加处理目标对象
        Object target = pjp.getTarget();
        // 为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        // 获取注解信息
        RetryOnFailure annotation = currentMethod.getAnnotation(RetryOnFailure.class);
        final int tryTime = annotation.tryTime();

        AtomicInteger ai = new AtomicInteger(0);
        do{
            try{
                log.info("**************第"+ai.get()+"次**************");
                return pjp.proceed();
            } catch (TryAgainException ex){
                ai.incrementAndGet();
                if(ai.get()>tryTime-1){
                    log.info("*****三次-over******");
                }
            }
        }while(ai.get()<tryTime);

        return null;
    }
}
