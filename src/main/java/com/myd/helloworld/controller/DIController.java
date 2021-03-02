package com.myd.helloworld.controller;

import com.myd.helloworld.config.User;
import com.myd.helloworld.service.BussniessMan;
import com.myd.helloworld.service.User2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/25 18:27
 * @Description:
 */
@Controller
@RequestMapping("/di")
public class DIController {

    @Autowired
    private BussniessMan bussniessMan;

    @Autowired
    private User2Service user2Service;

    @ResponseBody
    @RequestMapping("/test1")
    public String test1(){
        bussniessMan.service();
        return "success";
    }

    @ResponseBody
    @RequestMapping("/test2")
    public User printUser(){
        User user = new User();
        user.setId(2);
        user.setName("二曦");
        user.setNote("糖豆");
        user2Service.printUser(user);
        user2Service.manyAspect();
        return user;
    }



}
