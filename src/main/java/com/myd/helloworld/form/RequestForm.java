package com.myd.helloworld.form;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/9/3 10:47
 * @Description:
 */
@Getter
@Setter
public class RequestForm {
    private String name;
    private String addr;
    private BigDecimal weight;
}
