package com.myd.helloworld.intercept;

import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/26 09:45
 * @Description:
 */
@Data
public class Invocation {

    private Object[] params;

    private Method method;

    private Object target;

    public Invocation(Object[] params, Method method, Object target) {
        this.params = params;
        this.method = method;
        this.target = target;
    }

    public Object proceed() throws InvocationTargetException,IllegalAccessException{
        return method.invoke(target,params);
    }
}
