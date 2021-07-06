package com.myd.helloworld;

import com.myd.helloworld.common.contants.RedisConstants;
import com.myd.helloworld.util.AsyncUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/7/5 14:43
 * @Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@SpringBootTest(classes = HelloWorldApplication.class)
public class BaseTest {

    @Autowired
    private StringRedisTemplate redisTemplate;



    @Test
    public void test1(){
        String lockKey = "123";
        String uuid = UUID.randomUUID().toString();
        final Boolean success = redisTemplate.opsForValue().setIfAbsent(lockKey, uuid, 3, TimeUnit.MINUTES);
        if(!success){
            System.out.println("锁也存在了");
        }
        //指定lua脚本 并指定返回类型
        AsyncUtil.run(()-> System.out.println("*****"));
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript(RedisConstants.RELEASE_LOCK_LUA_SCRIPT,Long.class);
        final Long result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), uuid);
        System.out.println(result);

    }
}
