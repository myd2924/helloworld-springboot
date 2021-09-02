package com.myd.helloworld.base;

import lombok.NoArgsConstructor;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/8 10:44
 * @Description: 限流方法入参父类
 */
@NoArgsConstructor
public abstract class CentreRequest  extends CentreSerializable{
    private static final long serialVersionUID = 5010809403982852863L;

    public abstract boolean checkParam();

}
