package com.myd.helloworld.jms.consumer.resolver.support.exception;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/1 13:52
 * @Description:
 */
public class ResolverAbsentException extends Exception {
    public ResolverAbsentException(){
        super("resolver is absent");
    }
}
