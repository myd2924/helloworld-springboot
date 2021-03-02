package com.myd.helloworld.event;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/5 16:35
 * @Description:
 */
@Component
public class NotifyPublisher implements ApplicationContextAware {

    private  ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public void publishEvent(int status,String msg){
        if( status == 0){
            ctx.publishEvent(new NotifyEvent(this,msg));
        } else {
            ctx.publishEvent(new NotifyEvent(this,msg));
        }
    }
}
