package com.myd.helloworld.service;

import com.myd.helloworld.config.User;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 11:33
 * @Description:
 */
@Service
public class User2ServiceImpl implements User2Service {

    @Override
    public void printUser(User user) {
        if(null == user){
            throw new RuntimeException("user为null");
        }

        System.out.println("id:"+user.getId() + " name:"+user.getName() + " note:"+user.getNote() );
    }

    @Override
    public void manyAspect(User user){
        System.out.println("多个切面");
    }
}
