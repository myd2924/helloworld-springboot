package com.myd.helloworld.jms.product.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:54
 * @Description:
 */
@Slf4j
@Component
public class QueueMessageSender extends MessageSender{
    @Resource(name = "queueTemplate")
    private JmsTemplate queueTemplate;

    @Override
    protected void sendMessage(Destination destination, Object message) {
        queueTemplate.convertAndSend(destination,message);
    }
}
