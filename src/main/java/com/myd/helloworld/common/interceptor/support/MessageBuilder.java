package com.myd.helloworld.common.interceptor.support;

import com.myd.helloworld.entity.Message;
import com.myd.helloworld.jms.product.DestinationConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/2 11:10
 * @Description:
 */
@Service
@Slf4j
public class MessageBuilder extends AbstractMessageBuilder{


    public List<Message> buildMessage(final javax.jms.Message source) {
        if (Objects.isNull(source)) {
            return Collections.emptyList();
        }
        /*
         * 公共信息
         */
        final Message baseMessage = buildMessageBaseInfo(source);

        // 导入商品分享包
        if (baseMessage.getMessageName().contains(DestinationConstant.ITEM_CHANGE_TOPIC)) {
            return this.doBuildItemMessage(baseMessage);
        }

        return Collections.singletonList(emptyMessage(baseMessage));
    }

    private List<Message> doBuildItemMessage(Message baseMessage) {
        //业务转换
        final Map<?, ?> map = baseMessage.parseMessageAsMap();
        //todo
        return new ArrayList<>();
    }

    Message emptyMessage(Message source) {
        return source.setId(UUID.randomUUID().toString())
                //.setItemIdType(ItemIdType.UNDEFINED)
                //.setBusinessType(BusinessType.UNDEFINED)
                .setAddTime(new Date())
                .setUpdateTime(new Date());
    }
}
