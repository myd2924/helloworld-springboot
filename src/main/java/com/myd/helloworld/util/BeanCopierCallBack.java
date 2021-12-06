package com.myd.helloworld.util;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/12/6 17:39
 * @Description: TODO
 */
@FunctionalInterface
public interface BeanCopierCallBack<S,T> {
    void callback(S s,T t);
}
