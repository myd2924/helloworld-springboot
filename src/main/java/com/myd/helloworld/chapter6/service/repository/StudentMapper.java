package com.myd.helloworld.chapter6.service.repository;

import com.myd.helloworld.chapter6.service.criteria.StudentCriteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/7 10:51
 * @Description:
 */
@Repository
@Mapper
public interface StudentMapper{

    long countStu(@Param("criteria") StudentCriteria criteria);

}
