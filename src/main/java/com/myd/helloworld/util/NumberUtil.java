package com.myd.helloworld.util;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/9/2 14:06
 * @Description: 数字工具
 */
public class NumberUtil {

    public static long toLong(String value,long defaultValue){
        if(null == value){
            return defaultValue;
        }
        try{

            return Long.parseLong(value);
        } catch (NumberFormatException ee){
            return defaultValue;
        }
    }
}