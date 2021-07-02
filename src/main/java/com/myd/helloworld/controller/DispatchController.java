package com.myd.helloworld.controller;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/2 10:40
 * @Description:
 */

import com.myd.helloworld.jms.consumer.resolver.support.Dispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dispatch")
public class DispatchController {

    @Autowired
    private Dispatcher dispatcher;

    @RequestMapping("/start")
    public String start(){
        dispatcher.start();
        return "start";
    }
}
