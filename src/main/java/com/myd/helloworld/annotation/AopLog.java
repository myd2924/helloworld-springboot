package com.myd.helloworld.annotation;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/5 10:47
 * @Description:
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopLog {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 日志记录service实现
     * @return
     */
    String logService() default "operLogServiceImpl";


    /**
     * 是否保存请求的参数
     */
    boolean isSaveRequestData() default true;

}
