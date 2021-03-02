package com.myd.helloworld.chapter13.service.impl;

import com.myd.helloworld.chapter13.pojo.Seller;
import com.myd.helloworld.chapter13.service.ActiveMqSellerService;
import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter6.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/2 10:53
 * @Description:
 */
@Service
public class ActiveMqSellerServiceImpl implements ActiveMqSellerService {

    /**
     * 自定义地址
     */
    private static final String MY_DESTINATION = "my-destination";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private StudentService studentService;

    @Override
    public void sendSeller(Seller seller) {
        System.out.println("发送消息【"+seller+"】");
        jmsTemplate.convertAndSend(MY_DESTINATION,seller);
    }

    @Override
    @JmsListener(destination = MY_DESTINATION)
    public void receiveSeller(Seller seller) {
        System.out.println("接受消息：【"+seller+"】");
        Student build = Student.builder()
                .id(seller.getId())
                .name(seller.getUserName())
                .note(seller.getNote())
                .age(32)
                .build();
        studentService.insertStudent(build);

    }
}
