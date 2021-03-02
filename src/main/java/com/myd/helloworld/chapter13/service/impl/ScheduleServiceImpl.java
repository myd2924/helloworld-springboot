package com.myd.helloworld.chapter13.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/2 14:06
 * @Description:
 */
@Service
public class ScheduleServiceImpl {

    int count1 = 1;

    int count2 = 1;

    /**
     * 每隔一秒异步执行一次
     */
    @Async
    @Scheduled(fixedRate = 10000)
    public void job1(){
       /* System.out.println("【"+Thread.currentThread().getName()+"】" +
                " 【job1】 每秒执行一次，执行第【"+count1+"】");
        count1++;*/
    }

    /**
     * 每隔一秒异步执行一次
     */
    @Async
    @Scheduled(fixedRate = 10000)
    public void job2(){
       /* System.out.println("【"+Thread.currentThread().getName()+"】" +
                " 【job2】 每秒执行一次，执行第【"+count2+"】");
        count2++;*/
    }
}
