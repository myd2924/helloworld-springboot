package com.myd.helloworld.controller;

import com.myd.helloworld.event.NotifyPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/5 17:12
 * @Description: 事件测试
 */
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private NotifyPublisher notifyPublisher;

    @GetMapping("/sayHello")
    public String sayHello(String msg){
        notifyPublisher.publishEvent(0,msg);
        return "成功了";
    }
}
