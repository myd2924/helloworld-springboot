package com.myd.helloworld.base;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/8 11:04
 * @Description:
 */
public enum SortModel {
    DESC(1),
    ASE(-1);
    private int value;

    SortModel(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
