package com.myd.helloworld.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/25 18:42
 * @Description:
 */
@Component
@Primary
public class Cat implements Animal {
    @Override
    public void use() {
        System.out.println("猫【"+Cat.class.getSimpleName()+"】抓老鼠");
    }
}
