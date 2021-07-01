package com.myd.helloworld.jms.consumer.resolver.support;

import com.myd.helloworld.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private ExecutorManage executorManage;

    private AtomicBoolean isWorking = new AtomicBoolean(false);

    @Override
    public synchronized void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {

    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }
}
