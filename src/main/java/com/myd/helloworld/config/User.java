package com.myd.helloworld.config;

import lombok.Data;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/25 15:37
 * @Description:
 */

@Data
public class User {

    private long id;

    private String name;

    private String note;
}
