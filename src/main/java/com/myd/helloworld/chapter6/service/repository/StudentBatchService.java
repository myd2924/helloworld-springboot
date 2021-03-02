package com.myd.helloworld.chapter6.service.repository;

import com.myd.helloworld.chapter5.bean.Student;

import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/29 14:13
 * @Description:
 */
public interface StudentBatchService {

    int insertStudents(List<Student> students);
}
