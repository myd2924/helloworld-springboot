package com.myd.helloworld.chapter13.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/2 09:52
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seller implements Serializable{
    private static final long serialVersionUID = -4002324123272453223L;

    private Long id;

    private String userName;

    private String note;
}
