package com.myd.helloworld.controller;

import com.myd.helloworld.chapter13.pojo.Seller;
import com.myd.helloworld.chapter13.service.ActiveMqSellerService;
import com.myd.helloworld.chapter13.service.ActiveMqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/2 11:00
 * @Description:
 */
@Controller
@RequestMapping("/activeMq")
public class ActiveMqController {

    @Autowired
    private ActiveMqSellerService activeMqSellerService;

    @Autowired
    private ActiveMqService activeMqService;

    /**
     * 测试普通消息
     * @param msg
     * @return
     */
    @GetMapping("/msg")
    @ResponseBody
    public Object msg(String msg){
        activeMqService.sendMsg(msg);
        return result(true,msg);
    }



    /**
     * 测试发送seller
     * @param id
     * @param sellName
     * @param note
     * @return
     */
    @ResponseBody
    @GetMapping("/seller")
    public Object sendSeller(Long id,String sellName,String note){
        Seller seller = new Seller(id,sellName,note);
        activeMqSellerService.sendSeller(seller);
        return result(true,seller);
    }

    private Object result(Boolean b, Object msg) {

        Map map = new HashMap();
        map.put("success",b);
        map.put("msg",msg);

        return map;
    }


}
