package com.myd.helloworld.chapter5.dao;

import com.myd.helloworld.chapter5.bean.Student;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/27 12:55
 * @Description:
 */
@Repository
public interface MyBatisStudentDao {
    Student getStudent(Long id);
}
