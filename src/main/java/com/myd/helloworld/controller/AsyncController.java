package com.myd.helloworld.controller;

import com.myd.helloworld.chapter13.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/1 19:51
 * @Description: springBoot 异步线程测试
 */

@Controller
@RequestMapping("/async")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @ResponseBody
    @RequestMapping("/annoAsync")
    public Object asyncTest() throws InterruptedException {
        System.out.println("主线程名称：【"+ Thread.currentThread().getName() + " 】");
        asyncService.generalPort();
        System.out.println("hahhahhah");
        return "hahhahahah";
    }
}
