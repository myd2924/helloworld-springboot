package com.myd.helloworld.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/8 10:47
 * @Description: TODO
 */
@NoArgsConstructor
@Getter
@Setter
public abstract class CentreCutPageRequest extends CentreRequest{
    private static final long serialVersionUID = 651622969188892505L;

    private int pageNum;
    private int pageSize;

}
