package com.myd.helloworld.chapter6.service.impl;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter6.service.StudentService;
import com.myd.helloworld.chapter6.service.repository.StudentBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/29 14:14
 * @Description: 批量操作 局部失败 只会滚局部
 */
@Service
public class StudentBatchServiceImpl implements StudentBatchService {

    @Autowired
    private StudentService studentService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
    public int insertStudents(List<Student> students) {
        int count = 0;
        for (Student stu : students){
            try{
                count += studentService.insertStudent(stu);
            } catch (Exception e){
                System.out.println(stu);
                e.printStackTrace();
            }
        }
        return count;
    }
}
