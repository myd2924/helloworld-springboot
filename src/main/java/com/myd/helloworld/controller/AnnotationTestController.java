package com.myd.helloworld.controller;

import com.myd.helloworld.request.TestPramsRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/7 14:22
 * @Description: 注解测试
 */
@RestController
@RequestMapping("/annotest")
public class AnnotationTestController {

    @RequestMapping("/param")
    public void paramsTest(@Validated TestPramsRequest request){
        System.out.println("hahhaha");
    }
}
