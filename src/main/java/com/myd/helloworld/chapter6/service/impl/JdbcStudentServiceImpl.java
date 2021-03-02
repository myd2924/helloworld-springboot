package com.myd.helloworld.chapter6.service.impl;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter6.service.Jdbcservice;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Predicate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/28 09:23
 * @Description:
 */
@Service
public class JdbcStudentServiceImpl implements Jdbcservice{

    @Autowired
    private DataSource dataSource;

    @Override
    public int insertStu(Student stu) {
        Connection conn = null;
        int result = 0;
        try{
            //获取连接
            conn = dataSource.getConnection();
            //开启事务
            conn.setAutoCommit(false);
            //设置隔离级别
            conn.setTransactionIsolation(TransactionIsolationLevel.READ_COMMITTED.getLevel());
            PreparedStatement ps = conn.prepareStatement("insert into t_student( name,note,age) VALUES (?,?,?)");
            ps.setString(1,stu.getName());
            ps.setString(2,stu.getNote());
            ps.setInt(3,stu.getAge());

            //提交事务
            conn.commit();
        } catch (Exception e){
            if(null != conn){
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            try{
                if(null != conn && !conn.isClosed()){
                    conn.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return 0;
    }
}
