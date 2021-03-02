package com.myd.helloworld.chapter6.service.repository;

import com.myd.helloworld.chapter5.bean.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/29 09:50
 * @Description:
 */

@Repository
public interface StudentDao {

    Student getStudent(Long id);
    int insertStudent(Student stu);

    List<Student> getStudentLikeName(String name);
}
