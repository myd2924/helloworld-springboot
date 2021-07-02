package com.myd.helloworld.common.interceptor;

import com.myd.helloworld.common.interceptor.support.MessageBuilder;
import com.myd.helloworld.jms.JmsConstants;
import com.myd.helloworld.jms.consumer.resolver.support.DispatchTaskPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/2 11:03
 * @Description:
 */
@Component
@Aspect
@Slf4j
public class JmsListenerInterceptor {

    @Value("${app.name}")
    private String appName;

    @Resource
    private MessageBuilder messageBuilder;

    @Resource
    private DispatchTaskPool taskPool;

    @Around("@annotation(org.springframework.jms.annotation.JmsListener) && @annotation(com.myd.helloworld.annotation.JmsDispatcherInterceptor)")
    public Object jmsListenerAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        final Message source = this.deduceJmsMessage(joinPoint);
        try{
            final String fromApp = source.getStringProperty(JmsConstants.MESSAGE_SENDER_APP);
            if (StringUtils.equals(fromApp, appName)) {
                log.warn("可能循环发送消费MQ消息，请注意. message={}", source);
                return null;
            }
            List<com.myd.helloworld.entity.Message> messages = messageBuilder.buildMessage(source);
            taskPool.join(messages);
            return joinPoint.proceed();
        } finally {
            this.acknowledge(source);
        }


    }

    private void acknowledge( final javax.jms.Message source){
        try {
            source.acknowledge();
        } catch (JMSException e) {
            log.error("acknowledge error", e);
        }
    }

    private javax.jms.Message deduceJmsMessage(final ProceedingJoinPoint joinPoint){
        final Object[] args = joinPoint.getArgs();
        return Arrays
                .stream(Optional.ofNullable(args).orElse(new Object[0]))
                .filter(arg->arg instanceof javax.jms.Message)
                .map(arg->(javax.jms.Message)arg)
                .findFirst()
                .orElse(null);

    }

}
