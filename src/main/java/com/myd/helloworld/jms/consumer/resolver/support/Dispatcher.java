package com.myd.helloworld.jms.consumer.resolver.support;

import com.myd.helloworld.jms.consumer.resolver.MessageResolver;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:43
 * @Description: 调度器
 */
@Slf4j
@Component
public class Dispatcher implements SmartLifecycle{

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private DispatchMeta dispatchMeta;

    @Resource
    private ExecutorManager executorManager;

    private AtomicBoolean isWorking = new AtomicBoolean(false);
    private final StatusManager statusManager = StatusManager.getInstance();

    @PostConstruct
    public void postConstruct(){
        final String[] resolversNames = applicationContext.getBeanNamesForType(MessageResolver.class);
        final MessageResolver[] messageResolvers = Stream.of(resolversNames)
                .map(name -> applicationContext.getBean(name, MessageResolver.class))
                .filter(Objects::nonNull)
                .toArray(value -> new MessageResolver[resolversNames.length]);
        log.info("postConstruct完成，resolverSize:{}, resolversNames: {}", messageResolvers.length, resolversNames);
        ResolverManager.getInstance().register(messageResolvers);
    }

    @Override
    public synchronized void start() {
        executorManager.getMainExecutor().execute(()->{
            if(isWorking.compareAndSet(false,true)){
                statusManager.start();
                try {
                    while (statusManager.canRunning()) {
                        final DispatchTask task = dispatchMeta.getTaskPool().next();
                        if(Objects.nonNull(task) && CollectionUtils.isNotEmpty(task.getMessageIds())){
                            executorManager.getTaskExecutor().submit(Resolver.of(dispatchMeta,task));
                        }
                        try {
                            TimeUnit.MILLISECONDS.sleep(executorManager.getRestTimeMillis());
                        } catch (InterruptedException e) {
                            log.error("调度任务线程被中断", e);
                            Thread.currentThread().interrupt();
                        }
                    }
                } finally {
                    isWorking.set(false);
                }
            }
        });
    }

    /**
     * 正确的调用顺序是
     shutdown方法
     awaitTermination方法
     shutdownNow方法(发生异常或者是Timeout的时候)
     */
    @Override
    public synchronized void stop() {
        statusManager.stop();
        final ThreadPoolExecutor taskExecutor = executorManager.getTaskExecutor();
        final int waitTime = 30;
        taskExecutor.shutdown();
        try {
            if(!taskExecutor.awaitTermination(waitTime,TimeUnit.SECONDS)){
                taskExecutor.shutdownNow();
                /*dispatchMeta.getExceptionHandler().handle(new ExecutorShutdownTimeOutException(waitSeconds));
                if (!taskExecutor.awaitTermination(waitSeconds, TimeUnit.SECONDS)) {
                    log.warn("Pool did not terminate in " + waitSeconds + " seconds");
                }*/
            }
        } catch (InterruptedException e) {
            taskExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }

    @Override
    public boolean isRunning() {
        return statusManager.isRunning();
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {
        this.stop();
        callback.run();
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}
