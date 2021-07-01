package com.myd.helloworld.jms.consumer.resolver.support;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.ExtensionMethod;
import lombok.experimental.UtilityClass;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:48
 * @Description:  参考线程池状态位
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StatusManager {

    private static final StatusManager INSTANCE = new StatusManager();
    private final AtomicInteger state    = new AtomicInteger(Status.UNSTARTED.value);

    static StatusManager getInstance() {
        return INSTANCE;
    }

    /**
     * 启动
     */
    void start() {
        final Status status = Status.RUNNING;
        for (; ; ) {
            int c = state.get();
            if (c >= status.value || state.compareAndSet(c, status.value)) {
                break;
            }
        }
    }

    /**
     * 停止
     */
    void stop() {
        final Status status = Status.STOPPING;
        for (; ; ) {
            int c = state.get();
            if (c < status.value || state.compareAndSet(c, status.value)) {
                break;
            }
        }
    }

    /**
     * 是否正在运行
     */
    boolean isRunning() {
        return state.get() >= Status.SUSPENDED.value;
    }

    boolean canRunning() {
        return state.get() >= Status.RUNNING.value;
    }

    ///**
    // * 暂停
    // * FIXME前置状态可能是 繁忙
    // */
    //void suspend() {
    //    final Status status = Status.SUSPENDED;
    //    for (; ; ) {
    //        int c = state.get();
    //        if (c < status.value || state.compareAndSet(c, status.value)) {
    //            break;
    //        }
    //    }
    //}

    @Getter(AccessLevel.PACKAGE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    private enum Status {
        UNSTARTED(StatusHelper.UNSTARTED, "未运行"),
        RUNNING(StatusHelper.RUNNING, "运行中"),
        BUSYING(StatusHelper.BUSYING, "任务繁忙"),
        SUSPENDED(StatusHelper.SUSPENDED, "暂停中"),
        STOPPING(StatusHelper.STOPPING, "停止中"),
        TERMINATED(StatusHelper.TERMINATED, "停止完毕");
        private final int    value;
        private final String desc;
    }

    @UtilityClass
    @ExtensionMethod({StatusHelper.StatusBit.class})
    private static class StatusHelper {

        static final int UNSTARTED  = 0b0000;
        static final int RUNNING    = UNSTARTED.and(StatusBit.START).not(StatusBit.BUSY).not(StatusBit.SUSPEND).not(StatusBit.STOP); // 1111 15
        static final int BUSYING    = UNSTARTED.and(StatusBit.START).and(StatusBit.BUSY).not(StatusBit.SUSPEND).not(StatusBit.STOP); // 1101 13
        static final int SUSPENDED  = UNSTARTED.and(StatusBit.START).not(StatusBit.BUSY).and(StatusBit.SUSPEND).not(StatusBit.STOP); // 1011 11
        static final int STOPPING   = UNSTARTED.not(StatusBit.START).not(StatusBit.BUSY).not(StatusBit.SUSPEND).not(StatusBit.STOP); // 0111 7
        static final int TERMINATED = UNSTARTED.not(StatusBit.START).not(StatusBit.BUSY).not(StatusBit.SUSPEND).and(StatusBit.STOP); // 0110 6

        @AllArgsConstructor(access = AccessLevel.PRIVATE)
        private enum StatusBit {
            // 按优先级顺序排序: 高1位启动位(1启动)，2位暂停位(0暂停), 3位繁忙位(0繁忙), 4位停止位(0停止)
            START(false), SUSPEND(true), BUSY(true), STOP(true);

            private final boolean reverse; // true表示1为是, false表示1为否

            @SuppressWarnings("unused")
            static int and(int value, StatusBit s) {
                final int bit = StatusBit.values().length - s.ordinal() - 1;
                if (s.reverse) {
                    return value & (~(1 << bit));
                } else {
                    return value | (1 << bit);
                }
            }

            @SuppressWarnings("unused")
            static int not(int value, StatusBit s) {
                final int bit = StatusBit.values().length - s.ordinal() - 1;
                if (s.reverse) {
                    return value | (1 << bit);
                } else {
                    return value & (~(1 << bit));
                }
            }
        }

        //public static void main(String[] args) {
        //    System.out.println(RUNNING);
        //    System.out.println(BUSYING);
        //    System.out.println(SUSPENDED);
        //    System.out.println(STOPPING);
        //    System.out.println(TERMINATED);
        //}
    }
}
