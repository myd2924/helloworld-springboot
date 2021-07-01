package com.myd.helloworld.jms.consumer.resolver.support;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:45
 * @Description: 调度工单
 */

@Getter
@RequiredArgsConstructor(staticName = "of")
public class DispatchTask {

    private final String sliceId;
    private final List<String> messageId;

}
