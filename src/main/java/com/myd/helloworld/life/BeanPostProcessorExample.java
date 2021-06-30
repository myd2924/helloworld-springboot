package com.myd.helloworld.life;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/25 19:09
 * @Description: bean后置处理器
 */
@Component
public class BeanPostProcessorExample implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        /*System.out.println("BeanPostProcessor调用了postProcessBeforeInitialization" +
                " 方法，参数【"+bean.getClass().getSimpleName()+"】【"+beanName+"】");*/
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        /*System.out.println("BeanPostProcessor调用了postProcessAfterInitialization" +
                " 方法，参数【"+bean.getClass().getSimpleName()+"】【"+beanName+"】");*/
        return bean;
    }
}
