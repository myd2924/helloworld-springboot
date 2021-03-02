package com.myd.helloworld.chapter7.config;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/30 13:49
 * @Description:
 */
@Component
public class RedisMessageListener implements MessageListener{
    @Override
    public void onMessage(Message message, byte[] pattern) {
        //消息体
        String body = new String(message.getBody());
        //渠道名称
        String topic = new String(pattern);
        System.out.println(body);
        System.out.println(topic);
    }
}
