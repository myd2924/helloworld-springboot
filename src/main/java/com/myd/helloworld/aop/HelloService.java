package com.myd.helloworld.aop;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 09:27
 * @Description:
 */
public interface HelloService {
    void sayHello(String name);
    void invalidAopSayHello(String name);

    void oneAopSayHello(String name);
    void twoAopSayHello(String name);
    void threeAopSayHello(String name);
}
