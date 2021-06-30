package com.myd.helloworld.chapter13.service.impl;

import com.myd.helloworld.chapter13.service.ActiveMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/2 09:43
 * @Description:
 */
@Service
public class ActiveMqServiceImpl implements ActiveMqService {

    /**
     * 注入由spring boot 生产的jmsTemplate
     */
    @Autowired
    @Resource(name = "queueTemplate")
    private JmsTemplate jmsTemplate;


    @Override
    public void sendMsg(String msg) {
        System.out.println("发送消息【"+msg+"】");
        jmsTemplate.convertAndSend(msg);
        //自定义发送地址
        //jmsTemplate.convertAndSend("myDestination",msg);
    }

    @Override
    @JmsListener(destination = "${spring.jms.template.default-destination}")
    public void receiveMsg(String msg) {
        System.out.println("接收消息：【"+msg+"】");
    }
}
