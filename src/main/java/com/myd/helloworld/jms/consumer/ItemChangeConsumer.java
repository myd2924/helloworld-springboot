package com.myd.helloworld.jms.consumer;

import com.myd.helloworld.jms.product.DestinationConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 10:05
 * @Description: 商品变更消费
 */
@Component
@Slf4j
public class ItemChangeConsumer {

    @JmsListener(containerFactory = "",destination = DestinationConstant.ITEM_CHANGE_TOPIC)
    public void itemChange(final Message message){

    }

}
