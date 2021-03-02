package com.myd.helloworld.chapter13.service;

import com.myd.helloworld.chapter13.pojo.Seller;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/2/2 09:54
 * @Description:
 */
public interface ActiveMqSellerService {

    void sendSeller(Seller seller);

    void receiveSeller(Seller seller);
}
