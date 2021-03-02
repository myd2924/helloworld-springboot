package com.myd.helloworld.chapter10;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter6.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/30 15:48
 * @Description:
 */
@Controller
@RequestMapping("/mvc")
public class StudentMVCController {

    @Autowired
    private StudentService studentService;

    /**
     * 在无注解下 要求http请求参数与参数名称一致
     * @param intVal
     * @param longVal
     * @param str
     * @return 响应json参数
     */
    @ResponseBody
    @GetMapping("/no/annotation")
    public Object noAnnotation(Integer intVal , Long longVal, String str){
        Map map = new HashMap();
        map.put("intVal",intVal);
        map.put("longVal",longVal);
        map.put("str",str);
        return map;
    }

    /**
     * requestParam 通过注解制定参数
     * @param intVal
     * @param longVal
     * @param str
     * @return
     */
    @ResponseBody
    @GetMapping("/annotation")
    public Object requestParam(@RequestParam(value = "intVal_",required = false) Integer intVal , @RequestParam(value = "longVal_")Long longVal,@RequestParam(value = "str_") String str){
        Map map = new HashMap();
        map.put("intVal1_",intVal);
        map.put("longVal1_",longVal);
        map.put("str1_",str);
        return map;
    }

    /**
     * 请求参数名称要一致
     * @param intVal
     * @param longVal
     * @param str
     * @return
     */
    @ResponseBody
    @GetMapping("/requestArray")
    public Object requestParam( Integer[] intVal , Long[] longVal, String[] str){
        Map map = new HashMap();
        map.put("intVals_",intVal);
        map.put("longVals_",longVal);
        map.put("strs_",str);
        return map;
    }

    /**
     * 代码中控制器方法的参数标注了＠RequestBody ，所以处理器会采用请求体（ Body ）的 内容
     进行参数转换，而前端的请求体为 JSON 类型，所以首先它会调用 canRead 方法来确定请求体是否可
     如果判定可读后，接着就是使用 read 方法，将前端提交的用户 JSON 类型的请求体转换为控制
     器的用户（ User 类参数，这样控制器就能够得到参数了。
     上面的 MessageConve 接口只是将 TTP 请求

     httpMessageConvert
     * @param stu
     * @return
     */
    @ResponseBody
    @PostMapping("/student")
    public Object requestParam(@RequestBody Student stu){
        return stu;
    }

    @ResponseBody
    @GetMapping("/rest/{id}")
    public Object restGetById( @PathVariable("id") Long id){
        Student student = studentService.getStudent(id);
        return student;
    }

    /**
     * 转换失败 http://127.0.0.1:8081/mvc/convert?stu=1-myd-da-30
     * @param stu
     * @return
     */
    @GetMapping ("/convert")
    @ResponseBody
    public Student getUserByConverter (Student stu) {
        return stu;
    }
}
