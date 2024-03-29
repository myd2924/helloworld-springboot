package com.myd.helloworld.controller;

import com.myd.helloworld.annotation.AnnotationAop;
import com.myd.helloworld.form.RequestForm;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/9/3 09:52
 * @Description:
 */
@RestController
@RequestMapping("/aop")
public class AopControllerTest {

    @RequestMapping("/test1")
    public String test1(){
        System.out.println("我被定义aop了");
        return "定义";
    }

    @RequestMapping(value = "/test2")
    @AnnotationAop
    public String test2(@RequestBody RequestForm form){
        System.out.println(form.getName()+"-"+form.getWeight());
        System.out.println("我被注解和定义aop了");
        return "注解";
    }
}
