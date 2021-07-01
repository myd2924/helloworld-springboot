package com.myd.helloworld.jms.consumer.resolver.support;

import org.springframework.data.redis.core.TimeoutUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:43
 * @Description: 调度常量
 */
public interface DispatchContants {

    String PREFIX                        = "PC_M_D:";
    String TODO_SLICE_ID_KEY             = PREFIX + "TODO_SLICE_IDS";   // 等待处理的分片业务ID列表，即等待处理的chainMasterId
    String DOING_SLICE_ID_KEY            = PREFIX + "DOING_SLICE_IDS";  // 正在处理的分片业务ID列表
    String TODO_MSG_ID_OF_EACH_SLICE_ID  = PREFIX + "%s:TODO";          // 分片业务ID下等待处理的消息ID列表
    String DOING_MSG_ID_OF_EACH_SLICE_ID = PREFIX + "%s:DOING";         // 分片业务ID下正在处理的消息ID列表
    String MESSAGE_ID_KEY                = PREFIX + "MSG:%s";           // 消息内容
    String FAILED_TASK_ID_KEY            = PREFIX + "FAILED_MSG_ID";    // 处理失败的列表
    int    MAX_TASK_SIZE_EACH_TIME       = 100;
    long   DEFAULT_TTL                   = TimeoutUtils.toMillis(30, TimeUnit.MINUTES); // 任务处理超时时间。默认该之间之后超时强制释放任务占用


    /**
     * redis 的一些lua命令
     */
    String LUA_ADD_IF_ABSENT =
            "local key = KEYS[1];" +
            "local member = ARGV[1];" +
            "local score = ARGV[2];" +
            "if(redis.call('ZSCORE',key,member) == false) then"+
            "   if( 1 == tonumber(redis.call('ZADD',key,score,member))) then " +
            "      return member;" +
            "    end;" +
            " end;";
    String LUA_ZPOP_AND_PUSH_IF_ABSENT   =
            // @formatter:off
            "local sourceKey = KEYS[1];"
                    + "local destinationKey = KEYS[2];"
                    + "local time           = tonumber(ARGV[1]);"
                    + "local ttl            = tonumber(ARGV[2]);"
                    + "local expire         = time + ttl;"
                    + "redis.call('ZREMRANGEBYSCORE', destinationKey, '-inf', time );"
                    + "local datas = redis.call('ZRANGE', sourceKey, 0, -1, 'WITHSCORES');"
                    + "if ( datas ) then"
                    + "    local i = 0;"
                    + "    while i < # datas do"
                    + "        local member = datas[i+1];"
                    + "        local score = datas[i+2];"
                    + "        if ( redis.call('ZSCORE', destinationKey, member) == false ) then"
                    + "            redis.call('ZREM', sourceKey, member);"
                    + "            redis.call('ZADD', destinationKey, expire, member);"
                    + "            return member;"
                    + "        end;"
                    + "        i=i+2;"
                    + "    end;"
                    + "end;";

    default String todoKeyOfSliceId(final String sliceId) {
        return String.format(TODO_MSG_ID_OF_EACH_SLICE_ID, sliceId);
    }

    default String doingKeyOfSliceId(final String sliceId) {
        return String.format(DOING_MSG_ID_OF_EACH_SLICE_ID, sliceId);
    }

    default String messageIdKey(final String messageId) {
        return String.format(MESSAGE_ID_KEY, messageId);
    }
}
