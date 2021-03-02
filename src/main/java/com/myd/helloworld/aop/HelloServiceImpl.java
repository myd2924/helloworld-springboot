package com.myd.helloworld.aop;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 09:28
 * @Description:
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public void sayHello(String name) {
        if(null == name || "".equals(name.trim())){
            throw new RuntimeException("parameter is null !");
        }
        System.out.println("hello "+name);
    }
}
