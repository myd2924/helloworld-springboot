package com.myd.helloworld.db5;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 14:47
 * @Description:
 */

@Component
public class DataSourceShow implements ApplicationContextAware {

    ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        DataSource dataSource = applicationContext.getBean(DataSource.class);
        System.out.println("---------------");
        System.out.println(dataSource.getClass().getSimpleName());
        System.out.println("---------------");
    }
}
