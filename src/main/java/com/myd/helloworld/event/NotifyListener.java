package com.myd.helloworld.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/5 17:07
 * @Description:
 */
@Component
public class NotifyListener {

    @EventListener
    @Async
    public void sayHello(NotifyEvent notifyEvent){
        System.out.println("收到事件消息："+notifyEvent.getMsg());
    }
}
