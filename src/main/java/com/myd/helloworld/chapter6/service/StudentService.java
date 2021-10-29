package com.myd.helloworld.chapter6.service;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.except.BizException;
import com.myd.helloworld.except.TryAgainException;

import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/29 09:56
 * @Description:
 */
public interface StudentService {

    Student getStudent(Long id);

    int insertStudent(Student stu);

    List<Student> getStudentLikeName(String name);

    void updateStudent(Student stu) throws TryAgainException;

    void testBizException(String ca) throws BizException;
}
