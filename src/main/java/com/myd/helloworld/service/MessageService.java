package com.myd.helloworld.service;

import com.myd.helloworld.entity.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:06
 * @Description: 保存消息
 */
@Service
@Slf4j
public class MessageService {

    @Resource
    private MessageJpaRepository messageJpaRepository;

    /**
     * 保存消息
     * @param message
     */
    @Transactional
    public void save(final Message message){
        log.info("message={}",message);
        messageJpaRepository.save(message);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveNotException(final Message message) {
        log.info("message={}",message);
        try {
            messageJpaRepository.save(message);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
        }
    }
}
