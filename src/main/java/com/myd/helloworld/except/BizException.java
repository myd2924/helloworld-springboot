package com.myd.helloworld.except;

import lombok.*;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/10/29 13:45
 * @Description:
 * 需要注入很多的mapper接口或者另外的service接口，这时候就会写很多的@AutoWired注解，代码看起来很乱
 *  @RequiredArgsConstructor(onConstructor = @__(@Autowired))
 *
 *  写在类上可以代替@AutoWired注解，需要注意的是在注入时需要用final定义，或者使用@notnull注解
    private final User u;
 */
@Getter
@Setter
//@RequiredArgsConstructor(staticName = "of")
@NoArgsConstructor
@AllArgsConstructor
public class BizException extends RuntimeException {
    private static final long serialVersionUID = -7136807056208086867L;
    private String errorCode;
    private String errorMsg;
}
