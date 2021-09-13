package com.myd.helloworld.except;

import org.springframework.http.HttpStatus;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/9/13 22:18
 * @Description:
 */
public class TryAgainException extends RuntimeException{
    private Integer status = 0;

    public TryAgainException( String msg) {
        super(msg);
    }

    public TryAgainException(HttpStatus status, String msg) {
        super(msg);
        this.status = status.value();
    }


}
