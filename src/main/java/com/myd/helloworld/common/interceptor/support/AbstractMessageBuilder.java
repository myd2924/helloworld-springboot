package com.myd.helloworld.common.interceptor.support;

import com.myd.helloworld.entity.Message;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.Optional;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/2 11:11
 * @Description: 构建公用方法抽离
 */
@Slf4j
public abstract class AbstractMessageBuilder {

    @SneakyThrows
    Message buildMessageBaseInfo(final javax.jms.Message source) {
        if(source instanceof TextMessage){
            TextMessage textMessage = (TextMessage)source;
            return Message.builder()
                    .addTime(new Date())
                    .message(textMessage.getText())
                    .messageName(getMessageName(source))
                    .build();
        }
        return null;
    }

    private String getMessageName(final javax.jms.Message source){
        try {
            return Optional.ofNullable(source.getJMSDestination()).map(Object::toString).orElse(StringUtils.EMPTY);
        } catch (JMSException e) {
            log.error("Failed to read getJMSDestination", e);
            return StringUtils.EMPTY;
        }
    }
}
