package com.myd.helloworld.jms;

import com.myd.helloworld.jms.product.DestinationConstant;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Destination;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:49
 * @Description:
 */
@Configuration
public class MessageDestination {

    @Bean(name="itemChangeForOmsTopic")
    public Destination itemChangeForOmsTopic(){
        return new ActiveMQTopic(DestinationConstant.ITEM_CHANGE_TOPIC);
    }

}
