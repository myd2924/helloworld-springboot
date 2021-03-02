package com.myd.helloworld.chapter10.component;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import com.myd.helloworld.chapter5.bean.Student;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/30 16:35
 * @Description:  测试无效
 */
@Component
public class StringToStuConvert implements Converter<String,Student> {
    @Override
    public Student convert(String s) {
        Student stu = new Student();
        String[] split = s.split("-");
        Long id = Long.parseLong(split[0]);
        String name = split[1];
        String note = split[2];
        Integer age = Integer.parseInt(split[3]);
        stu.setId(id);
        stu.setName(name);
        stu.setNote(note);
        stu.setAge(age);
        return stu;
    }

    @Override
    public JavaType getInputType(TypeFactory typeFactory) {
        return null;
    }

    @Override
    public JavaType getOutputType(TypeFactory typeFactory) {
        return null;
    }
}
