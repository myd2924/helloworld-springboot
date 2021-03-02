package com.myd.helloworld.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/25 18:33
 * @Description:
 */
@Component
public class BussniessMan implements Person,BeanNameAware,BeanFactoryAware,
        ApplicationContextAware,InitializingBean,DisposableBean{

    private Animal animal;

    public BussniessMan(@Autowired @Qualifier("squirrel") Animal animal) {
        System.out.println("延迟依赖注入");
        this.animal = animal;
    }

    @Override
    public void service() {
        animal.use();
    }

    @Override
    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println(this.getClass().getSimpleName()+"调用了BeanNameAware的setBeanName");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println(this.getClass().getSimpleName()+"调用了BeanFactoryAware的setBeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(this.getClass().getSimpleName()+"调用了ApplicationContextAware的setApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(this.getClass().getSimpleName()+"调用了InitializingBean的afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println(this.getClass().getSimpleName()+"调用了DisposableBean的destroy");
    }

    @PostConstruct
    public void init(){
        System.out.println(this.getClass().getSimpleName()+"注解@PostConstruct 定义的自定义初始化方法");
    }

    @PreDestroy
    public void destory1(){
        System.out.println(this.getClass().getSimpleName()+"注解@PreDestroy 自定义的销毁方法");
    }


}
