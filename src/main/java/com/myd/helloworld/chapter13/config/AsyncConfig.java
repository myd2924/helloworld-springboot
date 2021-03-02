package com.myd.helloworld.chapter13.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/1 19:39
 * @Description:
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor(){
        ThreadPoolTaskExecutor excutor = new ThreadPoolTaskExecutor();
        excutor.setCorePoolSize(10);
        excutor.setMaxPoolSize(20);
        excutor.setQueueCapacity(2000);
        excutor.initialize();
        return excutor;
    }
}
