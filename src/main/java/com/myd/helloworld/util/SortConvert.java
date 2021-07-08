package com.myd.helloworld.util;

import com.myd.helloworld.base.SortEntry;
import com.myd.helloworld.base.SortModel;
import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/8 10:38
 * @Description:
 */
@UtilityClass
public class SortConvert {

    public Sort convert(List<SortEntry> sortEntries){
        if(CollectionUtils.isEmpty(sortEntries)){
            return null;
        }
        final List<Sort.Order> orders = sortEntries.stream().map(o -> {
            Sort.Direction direction = Sort.Direction.ASC;

            if (o.getMode() == SortModel.DESC) {
                direction = Sort.Direction.DESC;
            }

            Sort.Order order = new Sort.Order(direction, o.getField());
            return order;
        }).collect(Collectors.toList());


        return  Sort.by(orders);
    }

}
