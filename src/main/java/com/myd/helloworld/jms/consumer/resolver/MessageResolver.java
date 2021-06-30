package com.myd.helloworld.jms.consumer.resolver;


import com.myd.helloworld.entity.Message;
import org.apache.ibatis.executor.BatchResult;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:28
 * @Description:
 */
public interface MessageResolver {
    boolean canResolver(final Message message);

    void resolver(final Message message) throws Exception;

    default void reponseResult(BatchResult stringBatchResult){
        // todo 抛出失败结果
    }
}
