package com.myd.helloworld.base;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/8 11:19
 * @Description:
 */
public interface SortStrategy {

    default boolean available(String field) {
        return false;
    }

    String map(String field);
}
