package com.myd.helloworld.service;

import com.myd.helloworld.config.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/25 15:47
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    private User user;

    public String getUserName(){
        return user.getName();
    }
}
