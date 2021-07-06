package com.myd.helloworld.chapter13.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/6 10:46
 * @Description:
 */
@ConfigurationProperties(prefix = "async")
@Data
@Component
public class AsyncProperties {
    private int corePoolSize ;
    private int maxPoolSize ;
    private int queueCapacity ;
    private String threadNamePrefix ;
}
