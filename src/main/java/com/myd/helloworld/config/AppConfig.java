package com.myd.helloworld.config;

import com.myd.helloworld.chapter5.dao.MyBatisStudentDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/25 17:29
 * @Description:
 */
@Configuration
@ComponentScan(value = "com.myd.helloworld.config",lazyInit = true)
@ImportResource(value = ("classpath:spring-other.xml"))
public class AppConfig {

    @Autowired
    SqlSessionFactory sqlSessionFactory;

   /* @Bean
    public MapperFactoryBean<MyBatisStudentDao> initMybatisStudentDao(){
        MapperFactoryBean<MyBatisStudentDao> bean = new MapperFactoryBean();
        bean.setMapperInterface(MyBatisStudentDao.class);
        bean.setSqlSessionFactory(sqlSessionFactory);
        return bean;
    }*/


    @Bean(name = "user")
    public User getUser(){
        User usr = new User();
        usr.setId(1);
        usr.setName("马大曦");
        usr.setNote("大宝贝");
        return usr;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfig(){
        //定义扫描实例
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        //加载sqlSessionFactory,spring boot会自动生产sqlSessionFactory实例
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //定义扫描的包
        configurer.setBasePackage("com.myd.helloworld.*");
        //限定被标注@Respository的接口才被扫描
        configurer.setAnnotationClass(Repository.class);
        //通过继承某个接口限制扫描 一般使用不多
        //configurer.setMarkerInterface(......);
        return configurer;
    }


}
