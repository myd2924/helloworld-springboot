package com.myd.helloworld.jms.consumer.resolver.support;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:46
 * @Description: 线程池管理器
 */
@Component
@Getter(AccessLevel.PACKAGE)
@Slf4j
public class ExecutorManager {

    @Value("${message.dispatch.restTimeMill}")
    private long restTimeMillis;

    @Resource
    private TaskExecutor mainExecutor;
    private final ThreadPoolExecutor taskExecutor;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public ExecutorManager(){
        final SecurityManager s = System.getSecurityManager();
        final ThreadGroup threadGroup = (s!=null)?s.getThreadGroup():Thread.currentThread().getThreadGroup();
        final int availableProcessors = Runtime.getRuntime().availableProcessors();
        this.taskExecutor = new ThreadPoolExecutor(
                availableProcessors*5,
                availableProcessors*10,
                200L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                r->new Thread(threadGroup,r,"MessageResolve-"+threadNumber.getAndIncrement(),0),
        new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
