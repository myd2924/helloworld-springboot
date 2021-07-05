package com.myd.helloworld.chapter13.service.impl;

import com.alibaba.fastjson.JSON;
import com.myd.helloworld.chapter13.service.ActiveMqService;
import com.myd.helloworld.jms.product.DestinationConstant;
import com.myd.helloworld.jms.product.support.TopicMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    @Resource(name = "topicTemplate")
    private JmsTemplate topicJmsTempalte;

    @Autowired
    private TopicMessageSender topicMessageSender;

    @Resource(name = "itemChangeTopic")
    private Destination itemChangeTopic;


    @Override
    public void sendMsg(String msg) {
        System.out.println("发送消息【"+msg+"】");
        //jmsTemplate.convertAndSend(msg);
        //自定义发送地址
        //topicJmsTempalte.convertAndSend(DestinationConstant.ITEM_CHANGE_TOPIC,msg);
        Map<String, Object> map = new HashMap<>();
        map.put("type", "11");
        map.put("chainMasterId", "A44646");
        map.put("sys", "hahah");
        map.put("operation", "jjjj");
        map.put("ids", "3333");
        map.put("updateSkuIds", "madading");
        map.put("msg",msg);
        final String jsonString = JSON.toJSONString(map);
        topicMessageSender.sendMessage(itemChangeTopic,jsonString);

    }

    @Override
    @JmsListener(destination = "${spring.jms.template.default-destination}")
    public void receiveMsg(String msg) {
        System.out.println("接收消息：【"+msg+"】");
    }
}
