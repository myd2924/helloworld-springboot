package com.myd.helloworld.common.contants;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/5 13:39
 * @Description:  spring-boot-starter-data-redis 提供的返回类型里面不支持 Integer，用long。
 * 必须是公共的(public)静态(static)常量(final),三个修饰符可以交换位置
 * 所以直接用接口定义常量  省事了
 */
public interface RedisConstants {

    String RELEASE_LOCK_LUA_SCRIPT =
            " if redis.call('get',KEYS[1]) == KEYS[2] then" +
            "   return redis.call('del',KEYS[1]) " +
            " else " +
            "   return 0" +
            " end";

}
