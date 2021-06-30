package com.myd.helloworld.jms.product.support;

import com.myd.helloworld.service.MessageService;

import javax.annotation.Resource;
import javax.jms.Destination;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:53
 * @Description: TODO
 */
public abstract class MessageSender {
    @Resource
    private MessageService messageService;

    protected abstract void sendMessage(Destination destination, Object message);
}
