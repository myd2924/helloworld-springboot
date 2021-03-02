package com.myd.helloworld.request;

import lombok.Data;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/7 14:25
 * @Description:
 */
@Data
public class TestPramsRequest {

    @Max(value = 150,message = "年纪太大")
    @Min(value = 0,message = "年纪不能为负数")
    private Integer age;

    @Size.List({@Size(min = 1,message = "至少要有一个元素")})
    private List<String> list;

    @NonNull
    private String name;
}
