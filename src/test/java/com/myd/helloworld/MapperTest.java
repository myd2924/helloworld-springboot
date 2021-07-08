package com.myd.helloworld;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter6.service.criteria.StudentCriteria;
import com.myd.helloworld.chapter6.service.repository.StudentMapper;
import org.junit.Test;
import org.springframework.data.domain.Page;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/7 11:19
 * @Description: TODO
 */
public class MapperTest extends BaseTest {

    @Resource
    private StudentMapper studentMapper;

    @Test
    public void test1(){
        final long countStu = studentMapper.countStu(new StudentCriteria());
        System.out.println("*********student*********"+countStu);
        final Page<Student> page = studentMapper.queryStudents(new StudentCriteria(), 0, 10);
        System.out.println("****page*****"+page.getContent().get(0));
    }

}
