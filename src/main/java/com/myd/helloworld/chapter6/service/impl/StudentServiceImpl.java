package com.myd.helloworld.chapter6.service.impl;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter6.service.StudentService;
import com.myd.helloworld.chapter6.service.repository.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/29 09:57
 * @Description:
 */
@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentDao studentDao;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED,timeout = 1)
    public Student getStudent(Long id) {
        return studentDao.getStudent(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRES_NEW,timeout = 1)
    public int insertStudent(Student stu) {
        return studentDao.insertStudent(stu);
    }

    @Override
    public List<Student> getStudentLikeName(String name) {
        return studentDao.getStudentLikeName(name);
    }
}
