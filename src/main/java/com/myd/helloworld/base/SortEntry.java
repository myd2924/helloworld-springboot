package com.myd.helloworld.base;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/8 11:00
 * @Description:
 */
public class SortEntry extends CentreInfo{

    private static final long serialVersionUID = -5217565896548559547L;

    @Getter
    @Setter
    private String field;
    @Getter
    private SortModel mode;

    public SortEntry(){
        this.mode = SortModel.ASE;
    }

    public void setAsc(){
        this.mode = SortModel.ASE;
    }

    public void setDesc(){
        this.mode = SortModel.DESC;
    }

}
