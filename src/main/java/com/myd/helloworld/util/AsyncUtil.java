package com.myd.helloworld.util;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/6 10:12
 * @Description: 异步并发工具
 */
@Component
public class AsyncUtil {

    private static AsyncTaskExecutor executor;

    @Resource(name = "asyncExecutor")
    public void setExecutor(AsyncTaskExecutor executor) {
        AsyncUtil.executor = executor;
    }

    public static <V> CompletableFuture<V> supply(@NotNull Supplier<V> supplier){
        return CompletableFuture.supplyAsync(supplier,executor);
    }

    public static CompletableFuture<Void> run(@NotNull Runnable runnable){
        return CompletableFuture.runAsync(runnable,executor);
    }

    public static <T1,T2,T3,R> R parallel(@NotNull Supplier<T1> t1Supplier,
                                          @NotNull Supplier<T2> t2Supplier,
                                          @NotNull Supplier<T3> t3Supplier,
                                          @NotNull Function3<T1,T2,T3,R> function3){
        final CompletableFuture<T1> supply1 = AsyncUtil.supply(t1Supplier);
        final CompletableFuture<T2> supply2 = AsyncUtil.supply(t2Supplier);
        final CompletableFuture<T3> supply3 = AsyncUtil.supply(t3Supplier);

        return CompletableFuture.allOf(supply1,supply2,supply3).thenApply(item ->{
            final T1 t1 = supply1.join();
            final T2 t2 = supply2.join();
            final T3 t3 = supply3.join();
            return function3.apply(t1,t2,t3);
        }).join();

    }

    public static <V> List<V> parallel(@NotNull final Supplier<V> ...suppliers){
        final Collection<CompletableFuture<V>> completableFutureList = Arrays.stream(suppliers).map(AsyncUtil::supply).collect(Collectors.toList());
        return parallel(completableFutureList);
    }

    private static <V> List<V> parallel(@NotNull Collection<CompletableFuture<V>> completableFutureList) {
        return parallel(completableFutureList.toArray(new CompletableFuture[0]));
    }

    private static <V> List<V> parallel( @NotNull final CompletableFuture<V> ...futures) {
        final CompletableFuture<Void> done = CompletableFuture.allOf(futures);
        return done.thenApply(v->Arrays.stream(futures).map(CompletableFuture::join).collect(Collectors.toList())).join();
    }


}
