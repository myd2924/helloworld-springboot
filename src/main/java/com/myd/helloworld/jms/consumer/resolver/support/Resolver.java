package com.myd.helloworld.jms.consumer.resolver.support;

import com.myd.helloworld.entity.Message;
import com.myd.helloworld.jms.consumer.resolver.MessageResolver;
import com.myd.helloworld.jms.consumer.resolver.support.exception.ResolverAbsentException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.StopWatch;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:47
 * @Description:
 */
@Slf4j
@RequiredArgsConstructor(staticName = "of",access = AccessLevel.PACKAGE)
public class Resolver implements Runnable{

    private final DispatchMeta meta;
    private final DispatchTask task;

    @Override
    public void run() {
        if(Objects.isNull(meta) || CollectionUtils.isEmpty(task.getMessageIds())){
            return;
        }

        this.resolve(task);
    }

    private void resolve(final DispatchTask task) {
        for(final String messageId:task.getMessageIds()){
            final Message messageById = meta.getTaskPool().getMessageById(messageId);
            if(Objects.isNull(messageById)){
                log.warn("根据messageId从redis获取到的消息为null, chainMasterId:{}, messageId:{}", task.getSliceId(), messageId);
                continue;
            }
            try {
                this.resolve(messageById);
                meta.getTaskPool().asComplete(messageById);
            } catch (Exception e) {
                log.error("resolve message error, message={}", messageById, e);
                meta.getTaskPool().asFailed(messageById);
            } finally {
               // meta.getMonitor().warnCostIfNecessary(message);
            }
        }
    }

    private void resolve(final Message message) throws Exception{
        if(Objects.isNull(message)){
            return;
        }
        final Instant start = Instant.now();
        //StopWatch watch = new StopWatch();
        try{
            final MessageResolver messageResolver = Stream
                    .of(ResolverManager.getInstance().getResolvers())
                    .filter(r -> r.canResolver(message))
                    .findFirst()
                    .orElse(this.unknownMessageResolver());
            messageResolver.resolver(message);
            message.asComplete();
        } catch (Exception e){
            message.asFailed(e);
            throw e;
        } finally {
            message.setCost(Duration.between(start, Instant.now()).toMillis());
            try {
                meta.getMessageService().save(message);
            } catch (Exception e){
                //meta.getExceptionHandler().handle(new MessagePersistException(e), ExceptionSlogan.of("调度任务信息", message));
            }
        }
    }

    private MessageResolver unknownMessageResolver() {
        return new MessageResolver() {
            @Override
            public boolean canResolver(Message message) {
                return true;
            }

            @Override
            public void resolver(Message message) throws Exception {
                //异常处理
            }
        };
    }
}
