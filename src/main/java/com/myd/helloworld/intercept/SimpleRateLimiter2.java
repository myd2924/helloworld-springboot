package com.myd.helloworld.intercept;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/12/20 13:59
 * @Description: 简单java实现
 */
@Slf4j
public class SimpleRateLimiter2 {

    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    /**最后一次令牌发放时间*/
    private long timeStamp = System.currentTimeMillis();
    /**桶的容量*/
    private int capacity = 7;
    /**生成令牌的速度*/
    private int rate = 5;
    /**当前令牌数*/
    private int  tokens;

    public void acquire(){
        try{
            scheduledExecutorService.scheduleWithFixedDelay(()->{
                long now = System.currentTimeMillis();
                long tokenCal = tokens + (now-timeStamp)*rate/1000;
                int tokensInt = (int)(tokenCal);
                tokens = Math.min(tokensInt,capacity);
                timeStamp = now;

                //每隔0.5秒发送随机数量的请求
                int permits = (int) (Math.random() * 9) + 1;
                log.info("请求令牌数：" + permits + "，当前令牌数：" + tokens);

                if(permits>tokens){
                    log.info("限流了");
                } else {
                    tokens -= permits;
                    log.info("剩余令牌："+tokens);
                }
            },100,500, TimeUnit.MILLISECONDS);
        } catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SimpleRateLimiter2 limiter = new SimpleRateLimiter2();
        limiter.acquire();
    }
}
