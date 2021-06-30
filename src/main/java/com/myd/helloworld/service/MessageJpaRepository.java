package com.myd.helloworld.service;

import com.myd.helloworld.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:20
 * @Description:
 */
@Repository
public interface MessageJpaRepository extends JpaRepository<Message,String>{
}
