package com.myd.helloworld.chapter13.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/6 15:45
 * @Description:
 */
public class HandlerAsyncTaskExecutor implements AsyncTaskExecutor,InitializingBean,DisposableBean{

    private AsyncTaskExecutor executor;

    public HandlerAsyncTaskExecutor(AsyncTaskExecutor asyncTaskExecutor) {
        this.executor = asyncTaskExecutor;
    }

    @Override
    public void destroy() throws Exception {
        if(this.executor instanceof DisposableBean){
            ((DisposableBean) this.executor).destroy();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(this.executor instanceof InitializingBean){
            ((InitializingBean) this.executor).afterPropertiesSet();
        }

    }

    @Override
    public void execute(Runnable runnable, long l) {
        this.executor.execute(runnable,l);
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        return this.executor.submit(runnable);
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return this.executor.submit(callable);
    }

    @Override
    public void execute(Runnable runnable) {
        this.executor.execute(runnable);
    }
}
