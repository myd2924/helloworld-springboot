package com.myd.helloworld.service;

import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/25 18:33
 * @Description:
 */
@Component
public class Dog implements Animal {
    @Override
    public void use() {
        System.out.println("狗【"+Dog.class.getSimpleName()+"】是看门的");
    }
}
