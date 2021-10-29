package com.myd.helloworld.controller;

import com.myd.helloworld.chapter6.service.StudentService;
import com.myd.helloworld.response.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/10/29 14:48
 * @Description:  全局异常测试
 */
@RestController
@RequestMapping("/exception")
public class ExceptionController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/myException")
    public ResponseVo test(String my){
        studentService.testBizException(my);
        return ResponseVo.success();
    }

}
