package com.myd.helloworld.chapter5.respository;

import com.myd.helloworld.chapter5.bean.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 20:20
 * @Description:
 */
@Repository
public interface JpaStudentRespository extends JpaRepository<Student,Long>{

    @Query("select s from Student s where 1=1 and (s.name is null or s.name like concat('%',?1,'%') )" +
            " or (s.note is null or s.note like concat('%',?2,'%') )")
    List<Student> findStudents(final String name,final String note);

    List<Student> findByNameLike(final String name);
}
