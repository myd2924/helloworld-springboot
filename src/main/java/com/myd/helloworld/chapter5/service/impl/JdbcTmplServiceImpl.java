package com.myd.helloworld.chapter5.service.impl;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter5.service.JdbcTmplService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 17:37
 * @Description:
 */
@Service
public class JdbcTmplServiceImpl implements JdbcTmplService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 获取entity--bean映射关系
     * @return
     */
    private RowMapper<Student> getStuMapper(){
        RowMapper<Student> studentRowMapper = (ResultSet rs, int rownum)->{
            Student stu = new Student();
            stu.setId(rs.getLong("id"));
            stu.setName(rs.getString("name"));
            stu.setAge(rs.getInt("age"));
            stu.setNote(rs.getString("note"));
            return stu;
        };
        return studentRowMapper;
    }

    @Override
    public Student getStudent(Long id) {
        //执行的sql
        String sql = "select id,name,age,note from t_student where id = ?";
        //参数
        Object[] params = new Object[]{id};

        Student student = jdbcTemplate.queryForObject(sql, params, getStuMapper());
        return student;
    }

    @Override
    public List<Student> getStudents(String name, String note) {
        String sql = "select * from t_student " +
                " where name like concat('%',?,'%')" +
                " or note like concat('%',?,'%')";
        Object[] params = new Object[]{name,note};
        List<Student> students = jdbcTemplate.query(sql, params, getStuMapper());

        return students;
    }

    @Override
    public int insertStu(Student s) {
        String sql = "insert into t_student(name,age,note) values (?,?,?)";

        return jdbcTemplate.update(sql,s.getName(),s.getAge(),s.getNote());
    }

    @Override
    public int updateStu(Student s) {
        String sql = " update t_student set name=?,age=?,note=? " +
                " where id =?";

        return jdbcTemplate.update(sql,s.getName(),s.getAge(),s.getNote(),s.getId());
    }

    @Override
    public int deleteStu(long id) {
        String sql = "delete from t_student where id =?";
        return jdbcTemplate.update(sql,id);
    }
}
