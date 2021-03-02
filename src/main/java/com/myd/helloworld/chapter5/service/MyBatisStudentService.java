package com.myd.helloworld.chapter5.service;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter5.dao.MyBatisStudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/27 13:11
 * @Description:
 */
@Service
public class MyBatisStudentService {
    @Autowired
    private MyBatisStudentDao myBatisStudentDao;

    public Student getStu(Long id){
        return myBatisStudentDao.getStudent(id);
    }
}
