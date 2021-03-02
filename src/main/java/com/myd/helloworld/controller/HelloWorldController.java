package com.myd.helloworld.controller;

import com.myd.helloworld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/25 15:30
 * @Description:
 */
@Controller
public class HelloWorldController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/helloWorld")
    public String helloWorld() throws SQLException {
        return "hello World! "+userService.getUserName();
    }
}
