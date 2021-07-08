package com.myd.helloworld.base;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/8 10:50
 * @Description:
 */
public abstract class BaseSortRequest extends CentreCutPageRequest {

    private static final long serialVersionUID = -2440409809165753214L;

    private List<SortEntry> sorts = new ArrayList<>(0);

    @Setter
    @Getter
    private SortStrategy sortStrategy;

    public void addSorts(String field,SortModel model){
        if(StringUtils.isNotBlank(field)){
            String _field = this.sortStrategy != null ? this.sortStrategy.map(field):field;
            boolean available = this.sortStrategy != null ?this.sortStrategy.available(_field):true;
            if(available){
                if(model ==null){
                    model = SortModel.ASE;
                }

                SortEntry se = new SortEntry();
                se.setField(_field);
                if(model == SortModel.DESC){
                    se.setDesc();
                }

                this.sorts.add(se);
            }
        }
    }

}
