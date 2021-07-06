package com.myd.helloworld;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter6.service.StudentService;
import com.myd.helloworld.util.AsyncUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/6 17:44
 * @Description:
 */
public class ParallelTest extends BaseTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void testParallel(){
        final List<Student> list = AsyncUtil.parallel(
                () -> studentService.getStudent(1L),
                () -> studentService.getStudent(67L),
                () -> studentService.getStudent(68L),
                (stu1, stu2, stu3) -> {
                    List<Student> result = new ArrayList<>();
                    result.add(stu1);
                    result.add(stu2);
                    result.add(stu3);
                    return result;

                });
        System.out.println(list.size());
        //list.parallelStream().peek(u-> System.out.println(u));
        list.forEach(u-> System.out.println(u));

    }

}
