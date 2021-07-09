package com.myd.helloworld;

import com.myd.helloworld.aop.HelloService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/9 10:16
 * @Description:
 */
public class AopInvailTest extends BaseTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void test1(){
        helloService.sayHello("马晨曦");
        helloService.invalidAopSayHello("丁业曦");
        helloService.oneAopSayHello("老大");
        helloService.twoAopSayHello("老二");
        helloService.threeAopSayHello("老三");
    }

}
