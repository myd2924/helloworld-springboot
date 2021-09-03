package com.myd.helloworld.aop;

import com.myd.helloworld.base.CentreRequest;
import com.myd.helloworld.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.logging.log4j.util.PropertiesUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/9/1 17:56
 * @Description:
 *
 * 原来的code:
 *  private RelaxedPropertyResolver relaxedPropertyResolver;
 *  RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource");
 *  propertyResolver.getSubProperties("....");
 */

@Aspect
@Component
@Slf4j
public class CoreMethodAspectLimit implements EnvironmentAware,DisposableBean,InitializingBean{
    /**
     * 默认限制数
     */
    private final static long DEFAULT_LIMIT = 500;
    /**
     * 环境属性值
     */
    private Properties properties;

    private static ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void postConstruct(){
        scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().daemon(false).namingPattern("aop-counter-schedule").build());
    }

    /**
     * 保存接口控制器信息
     */
    private static final Map<String,AccessProviderGuard> accessProviderGuardMap = new HashMap();

    @Pointcut("execution(public * com.myd.helloworld.chapter6.service.impl.*Impl.*(..))")
    public void pointcut(){}

    @Pointcut(value = "@annotation(com.myd.helloworld.annotation.AnnotationAop)")
    public void annotationCut(){}

    /**
     * 定义需要匹配的切点表达式，同时需要匹配参数
     * @param request
     */
    @Order(0)
    @Before("pointcut() && args(request)")
    public void check(CentreRequest request){
        try{
            request.checkParam();
        } catch (Exception e){
            log.error(""+e);
        }
    }

    @Order(1)
    @Before("pointcut() || annotationCut()")
    public void access(JoinPoint jp){
        try{
            if(MapUtils.isNotEmpty(accessProviderGuardMap)){
                String apiName = jp.getSignature().getDeclaringType().getInterfaces()[0].getName()+"."+jp.getSignature().getName();
                final AccessProviderGuard providerGuard = accessProviderGuardMap.get(apiName);
                if(null != providerGuard && !providerGuard.checkPassed()){
                    throw new Exception("22222");
                }
            }
        } catch (Exception e){
            // 这里抛出的商品中心异常，只会是流量控制异常，直接向上抛
            //throw e;
        }
    }

    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint){
        try {
           return joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            //异常处理
            return throwable;
        }
    }



    @Override
    public void afterPropertiesSet() throws Exception {
        final Set<String> stringSet = this.properties.stringPropertyNames();
        for(String key: stringSet){
            final String limitCount = this.properties.getProperty(key);
            final long accessLimit = NumberUtil.toLong(limitCount, DEFAULT_LIMIT);
            accessProviderGuardMap.put(key,new AccessProviderGuard(key,accessLimit));
        }
    }


    @Override
    public void setEnvironment(Environment environment) {
        Iterable<ConfigurationPropertySource> sources = ConfigurationPropertySources.get(environment);
        Binder binder = new Binder(sources);
        BindResult<Properties> bindResult = binder.bind("access.limit", Properties.class);
        this.properties= bindResult.get();
    }

    @Override
    public void destroy() throws Exception {
        try{
            accessProviderGuardMap.values().forEach(AccessProviderGuard::destory);
        } catch (Exception ex){
            log.warn("Cancel Timer of ProviderAccessGuard failed.", ex);
        }
    }


    private static class AccessProviderGuard {
        /**
         * 接口名
         */
        private final String apiName;
        /**
         * 限流数
         */
        private final long accessLimit;

        /**
         * 计数器
         */
        private final AtomicLong counter;



       // private final Timer timer;

        AccessProviderGuard(String apiName,long accessLimit){
            this.apiName = apiName;
            this.accessLimit = accessLimit;
            counter = new AtomicLong(0);
           // this.timer = new Timer();
            //scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().daemon(false).namingPattern("aop-counter-schedule").build());
            scheduledExecutorService.scheduleAtFixedRate(()-> counter.set(0),1,1, TimeUnit.SECONDS);
        }

        /**
         * 判断是否能访问接口
         * @return
         */
        boolean checkPassed(){
            long count = counter.incrementAndGet();
            boolean passed = count <= accessLimit;
            if(!passed){
                log.error("接口每秒流量超过上限：api={}，limit={}，count={}", apiName, accessLimit, count);
            }
            return passed;
        }

        /**
         * https://blog.csdn.net/weixin_43271086/article/details/105438462
         * 终端线程
         */
        void destory(){
            /*if (timer != null) {
                timer.cancel();
                timer.purge();
            }*/
            try{
                scheduledExecutorService.shutdown();

                if(!scheduledExecutorService.awaitTermination(1,TimeUnit.SECONDS)){
                    scheduledExecutorService.shutdownNow();
                }
            } catch (InterruptedException e){
                log.error("awaitTermination interrupted: " + e);
                scheduledExecutorService.shutdownNow();
            }
        }


    }
}
