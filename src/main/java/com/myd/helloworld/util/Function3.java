package com.myd.helloworld.util;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/6 17:05
 * @Description: TODO
 */
@FunctionalInterface
public interface Function3<T1,T2,T3,R> {
    R apply(T1 t1,T2 t2,T3 t3);
}
