package com.myd.helloworld.chapter6.service.criteria;

import lombok.*;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/7 10:54
 * @Description:
 */

@Getter
@Builder
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class StudentCriteria {

    private Long id ;
    private String name ;

}
