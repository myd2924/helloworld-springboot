package com.myd.helloworld.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/10/29 11:27
 * @Description: 统一响应vo
 */
@Getter
@Setter
public class ResponseVo {
    private String code;
    private String msg;
    private Object data;

    public ResponseVo() {
    }

    public ResponseVo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseVo(String code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 成功
     * @return
     */
    public static ResponseVo success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResponseVo success(Object data) {
        ResponseVo rv = new ResponseVo();
        rv.setCode("0");
        rv.setMsg("操作成功");
        rv.setData(data);
        return rv;
    }

    /**
     * 失败
     * @param code
     * @param msg
     * @return
     */
    public static ResponseVo error(String code, String msg) {
        ResponseVo rv = new ResponseVo();
        rv.setCode(code);
        rv.setMsg(msg);
        rv.setData(null);
        return rv;
    }

    /**
     * 失败
     * @param msg
     * @return
     */
    public static ResponseVo error(String msg) {
        ResponseVo rv = new ResponseVo();
        rv.setCode("-1");
        rv.setMsg(msg);
        rv.setData(null);
        return rv;
    }

}
