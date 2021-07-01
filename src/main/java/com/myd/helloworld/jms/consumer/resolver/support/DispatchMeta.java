package com.myd.helloworld.jms.consumer.resolver.support;

import com.myd.helloworld.service.MessageService;
import lombok.Getter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:44
 * @Description: 调度器元数据
 */
@Component
@Getter
public class DispatchMeta {

    @Resource
    private DispatchTaskPool taskPool;
    @Resource
    private MessageService messageService;
    //告警配置

}
