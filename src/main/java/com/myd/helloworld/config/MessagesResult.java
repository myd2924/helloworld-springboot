package com.myd.helloworld.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/10/29 11:19
 * @Description:
 */
@Component
public class MessagesResult {

    @Autowired
    private MessageSource messageSource;

    public  String gotDesc(String key){
        final String message = messageSource.getMessage(key, null,null);
        return message ;
    }

}
