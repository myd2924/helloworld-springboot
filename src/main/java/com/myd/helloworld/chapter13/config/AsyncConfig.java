package com.myd.helloworld.chapter13.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

    private AsyncProperties asyncProperties;

    @Autowired
    public AsyncConfig(AsyncProperties asyncProperties){
        this.asyncProperties = asyncProperties;
    }

    @Override
    @Bean(name = "asyncExecutor")
    public Executor getAsyncExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncProperties.getMaxPoolSize());
        executor.setQueueCapacity(asyncProperties.getQueueCapacity());
        executor.initialize();
        return new HandlerAsyncTaskExecutor(executor);
    }
}
