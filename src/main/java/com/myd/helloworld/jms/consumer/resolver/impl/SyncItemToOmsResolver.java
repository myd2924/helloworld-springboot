package com.myd.helloworld.jms.consumer.resolver.impl;

import com.myd.helloworld.entity.Message;
import com.myd.helloworld.jms.consumer.resolver.MessageResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:36
 * @Description:
 */
@Service
@Slf4j
public class SyncItemToOmsResolver implements MessageResolver{
    @Override
    public boolean canResolver(Message message) {
        //业务判断
        return true;
    }

    @Override
    public void resolver(Message message) throws Exception {
        log.info("同步商品到OMS消息处理开始....msg={}", message);
        Map messageAsMap = message.parseMessageAsMap();
        //具体业务操作 todo
    }
}
