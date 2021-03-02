package com.myd.helloworld.chapter5.service;

import com.myd.helloworld.chapter5.bean.Student;

import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 17:36
 * @Description:
 */
public interface JdbcTmplService {
    Student getStudent(Long id);

    List<Student> getStudents(String name,String note);

    int insertStu(Student s);

    int updateStu(Student s);

    int deleteStu(long id);
}
