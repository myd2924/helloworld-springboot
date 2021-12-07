package com.myd.helloworld.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/12/6 18:07
 * @Description: 限流器注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestLimiter {
    /**
     * 每秒令牌数
     * @return
     */
    double QPS() default 10;

    /**
     * 获取令牌超时时间 500
     * @return
     */
    long timeout() default 500;

    /**
     * 时间单位
     * @return
     */
    TimeUnit timeunit() default TimeUnit.SECONDS;

    /**
     * w无法获取令牌  提示文案
     * @return
     */
    String msg() default "服务器爆了，请稍后再试";
}
