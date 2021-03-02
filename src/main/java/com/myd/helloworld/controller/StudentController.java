package com.myd.helloworld.controller;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter5.respository.JpaStudentRespository;
import com.myd.helloworld.chapter5.service.JdbcTmplService;
import com.myd.helloworld.chapter5.service.MyBatisStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 19:32
 * @Description:
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private JdbcTmplService jdbcTmplService;

    @Autowired
    private JpaStudentRespository jpaStudentRespository;

    @Autowired
    private MyBatisStudentService myBatisStudentService;

    @ResponseBody
    @RequestMapping("/getStu")
    public Student getStudent(long id){
        Student student = jdbcTmplService.getStudent(id);
        return student;
    }

    @ResponseBody
    @RequestMapping("/addStu")
    public int addStudent(String name,Integer age,String note){
        Student stu = new Student();
        stu.setName(name);
        stu.setAge(age);
        stu.setNote(note);
        return jdbcTmplService.insertStu(stu);
    }

    @ResponseBody
    @RequestMapping("/findStus")
    public List<Student> addStudent(String name, String note){
        return jdbcTmplService.getStudents(name,note);
    }

    @ResponseBody
    @RequestMapping("/jpaStu")
    public Student getStudentByJpa(long id){
        Student one = jpaStudentRespository.getOne(id);
        return one;
    }

    @ResponseBody
    @RequestMapping("/jpaStus")
    public List<Student> getStudentByJpa(String name, String note){
        List<Student> students = jpaStudentRespository.findStudents(name, note);
        return students;
    }

    @ResponseBody
    @RequestMapping("/jpaOneStu")
    public List<Student> findStudentByJpa(String name){
        List<Student> students = jpaStudentRespository.findByNameLike(name);
        return students;
    }

    @ResponseBody
    @RequestMapping("/myBatisStu")
    public Student getStudentByBatis(Long id){
        Student stu = myBatisStudentService.getStu(id);
        return stu;
    }
}
