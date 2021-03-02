package com.myd.helloworld.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/5 16:15
 * @Description: 事件源
 */
public class NotifyEvent extends ApplicationEvent {
    private  String msg;

    public NotifyEvent(Object source,String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
