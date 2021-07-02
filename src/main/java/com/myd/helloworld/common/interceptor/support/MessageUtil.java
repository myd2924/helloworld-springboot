package com.myd.helloworld.common.interceptor.support;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.TextMessage;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/2 11:13
 * @Description: TODO
 */
@Slf4j
@UtilityClass //final + static
public class MessageUtil {

    public String getMessageBody(final javax.jms.Message source){
        try{
            if(source instanceof TextMessage){
                return ((TextMessage) source).getText();
            } else if (source instanceof MapMessage) {
                final MapMessage mapMessage = (MapMessage) source;
                final Enumeration enumeration = mapMessage.getMapNames();
                final Map<String, Object> map = new HashMap<>();
                while (enumeration.hasMoreElements()) {
                    Object k = enumeration.nextElement();
                    String key = k instanceof String ? (String) k : k.toString();
                    Object value = mapMessage.getObject(key);
                    map.put(key, value);
                }return JSON.toJSONString(map);
            }/*else if (source instanceof ObjectMessage) {
                final Serializable obj = ((ObjectMessage) source).getObject();
                return obj.toString();
            } else if (message instanceof BytesMessage) {
            } else if (message instanceof StreamMessage) {
            }*/
            return JSON.toJSONString(source);
        } catch (JMSException e){
            log.error("Failed to read messageBody", e);
            return StringUtils.EMPTY;
        }
    }
}
