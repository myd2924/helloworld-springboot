package com.myd.helloworld.chapter13.service.impl;

import com.myd.helloworld.chapter13.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/1 19:47
 * @Description:
 */
@Service
public class AsyncServiceImpl implements AsyncService{
    /**
     * 声明使用异步调用
     */
    @Override
    @Async
    public void generalPort() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("异步线程名称：【"+ Thread.currentThread().getName() + " 】");
    }
}
