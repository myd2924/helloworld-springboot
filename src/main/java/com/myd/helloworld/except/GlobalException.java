package com.myd.helloworld.except;

import com.myd.helloworld.config.MessagesResult;
import com.myd.helloworld.response.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/10/29 11:24
 * @Description: 全局异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalException extends RuntimeException{
    private static final long serialVersionUID = -5660566372226580912L;

    @Autowired
    private MessagesResult messagesResult;

    @ExceptionHandler(value = TryAgainException.class)
    public ResponseVo tryAgainException(TryAgainException tryEx){
        log.warn("",tryEx.getMessage());
        return ResponseVo.error(tryEx.getMessage());
    }

    @ExceptionHandler(value = BizException.class)
    public ResponseVo bizException(BizException biz){
        log.warn("",biz.getMessage());
        return ResponseVo.error(biz.getErrorCode(),messagesResult.gotDesc(biz.getErrorCode()));
    }
}
