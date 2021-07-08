package com.myd.helloworld.chapter6.service.repository;

import com.myd.helloworld.chapter5.bean.Student;
import com.myd.helloworld.chapter6.service.criteria.StudentCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/7 10:51
 * @Description:
 */
@Repository
@Mapper
public interface StudentMapper{
    /**
     * 统计数量
     * @param criteria
     * @return
     */
    long countStu(@Param("criteria") StudentCriteria criteria);

    /**
     * 列表
     * @param criteria
     * @param pageNum
     * @param pageSize
     * @return
     */
    default Page<Student> queryStudents(@Param("criteria") @Nonnull StudentCriteria criteria, @Nonnegative int pageNum, @Nonnegative int pageSize){
        final long total = this.countStu(criteria);
        final int offset = pageNum * pageSize;
        final List<Student> content = total > 0?this.doQueryStudents(criteria,offset,pageSize): Collections.EMPTY_LIST;
        return new PageImpl<>(content,PageRequest.of(pageNum,pageSize),total);
    }

    /**
     * 列表
     * @param criteria
     * @param offset
     * @param pageSize
     * @return
     */
    List<Student> doQueryStudents(@Param("criteria") StudentCriteria criteria, @Nonnegative int offset, @Nonnegative int pageSize);

    /**
     * Pageable 分页+排序
     * @param criteria
     * @param pageable
     * @return
     */
    List<Student> doQueryStudentsByPageable(@Param("criteria") StudentCriteria criteria, Pageable pageable);

}
