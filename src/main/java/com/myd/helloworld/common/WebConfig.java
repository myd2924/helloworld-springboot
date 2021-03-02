package com.myd.helloworld.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/9 14:46
 * @Description: TODO
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private MyInterceptor1 myInterceptor1;

    @Autowired
    private MyInterceptor2 myInterceptor2;

    @Autowired
    private MyInterceptor3 myInterceptor3;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor1);
        registry.addInterceptor(myInterceptor2);
        registry.addInterceptor(myInterceptor3);
    }
}
