package com.myd.helloworld.controller;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter6.service.Jdbcservice;
import com.myd.helloworld.chapter6.service.StudentService;
import com.myd.helloworld.chapter6.service.repository.StudentBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/28 09:39
 * @Description:
 */
@Controller
@RequestMapping("/student6")
public class Student6Controller {

    @Autowired
    private Jdbcservice jdbcservice;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentBatchService studentBatchService;

    @ResponseBody
    @RequestMapping("/insert6one")
    public int insertStu(String name,String note,Integer age){
        Student stu = new Student();
        stu.setName(name);
        stu.setNote(note);
        stu.setAge(age);
        return jdbcservice.insertStu(stu);
    }


    @ResponseBody
    @RequestMapping("/insert4batis")
    public int insertStuBySpring(String name,String note,Integer age){
        Student stu = new Student();
        stu.setName(name);
        stu.setNote(note);
        stu.setAge(age);
        return studentService.insertStudent(stu);
    }

    @ResponseBody
    @RequestMapping("/getStudentByName")
    public List<Student> getStudentByName(String name) {
        return studentService.getStudentLikeName(name);
    }

    @ResponseBody
    @RequestMapping("/insertStus")
    public Map<String,Object> insertStus(){
        Student stu1 = Student.builder()
                .name("马红梅")
                .note("我的大姐")
                .age(45)
                .build();
        Student stu2 = Student.builder()
                .name("马永梅")
                .note("我的二姐")
                .age(43)
                .build();
        Student stu3 = Student.builder()
                .name("马永娟")
                .note("我的三姐")
                .age(35)
                .build();
        Student stu4 = Student.builder()
                .name("马元丁m,dsnfkjsndjkfjskhfkhshfshjdfhsdhfjshjfhsjhdhfjshfjsjbfjsabfjajfbjasbfjsabjfbsjbfjbsjf")
                .note("我自己")
                .age(320000)
                .build();
        List<Student> list = new ArrayList<>();
        list.add(stu1);
        list.add(stu2);
        list.add(stu3);
        list.add(stu4);
        int count = studentBatchService.insertStudents(list);
        Map<String,Object> result = new HashMap<>();
        result.put("sucess",count);
        result.put("student",list);
        return result;

    }
}
