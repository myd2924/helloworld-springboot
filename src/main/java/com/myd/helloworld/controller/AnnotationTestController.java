package com.myd.helloworld.controller;

import com.myd.helloworld.annotation.RequestLimiter;
import com.myd.helloworld.request.TestPramsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/7 14:22
 * @Description: 注解测试
 */
@RestController
@RequestMapping("/annotest")
@Slf4j
public class AnnotationTestController {

    private volatile AtomicInteger count = new AtomicInteger(0);

    @RequestMapping("/param")
    public void paramsTest(@Validated TestPramsRequest request){
        System.out.println("hahhaha");
    }

    @RequestLimiter(QPS = 10D,timeout = 500L,timeunit = TimeUnit.MILLISECONDS,msg = "让服务器休息一会吧！")
    @RequestMapping("/limitTest")
    public String testInterceptor(){
        log.info("myd=" + count);
        return "疯狂："+count.getAndIncrement() ;
    }
}
