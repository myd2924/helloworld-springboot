package com.myd.helloworld.chapter13.service;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/2 09:41
 * @Description:
 */
public interface ActiveMqService {
    void sendMsg(String msg);
    void receiveMsg(String msg);
}
