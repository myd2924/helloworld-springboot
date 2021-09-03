package com.myd.helloworld.common.config;

import com.myd.helloworld.common.filter.Filter1;
import com.myd.helloworld.common.interceptor.MyInterceptor1;
import com.myd.helloworld.common.interceptor.MyInterceptor2;
import com.myd.helloworld.common.interceptor.MyInterceptor3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/9 14:46
 * @Description:
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

    @Bean
    public FilterRegistrationBean registerFilter(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new Filter1());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("Filter1");
        registrationBean.setOrder(1);
        return registrationBean;
    }

}
