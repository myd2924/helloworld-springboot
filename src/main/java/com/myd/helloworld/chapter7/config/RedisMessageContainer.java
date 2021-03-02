package com.myd.helloworld.chapter7.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/30 13:55
 * @Description:
 */
@Configuration
public class RedisMessageContainer {

    //任务池
    private ThreadPoolTaskScheduler taskScheduler = null;

    @Autowired
    private RedisConnectionFactory connectionFactory = null;

    @Autowired
    private MessageListener redisMessageListener = null;


    @Bean
    public ThreadPoolTaskScheduler getInitTaskScheduler(){
        if(null != taskScheduler){
            return taskScheduler;
        }
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }

    @Bean
    public RedisMessageListenerContainer initRedisContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // redis的连接工厂
        container.setConnectionFactory(connectionFactory);
        //设置运行线程池
        container.setTaskExecutor(getInitTaskScheduler());
        //定义监听渠道 名称为topic1
        Topic topic = new ChannelTopic("topic1");
        //使用监听器监听 redis的消息
        container.addMessageListener(redisMessageListener,topic);

        return container;

    }

}
