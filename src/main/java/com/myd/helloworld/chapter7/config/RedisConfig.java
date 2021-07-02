package com.myd.helloworld.chapter7.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.Objects;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/29 15:39
 * @Description:  ；两种方式 一 代码写入  二 application属性文件里配置
 */
@Configuration
@ConditionalOnClass({JedisConnection.class, RedisOperations.class, Jedis.class})
public class RedisConfig {
    //private RedisConnectionFactory redisConnectionFactory = null;

   /* @Bean(name = "RedisConnectionFactory")
    public RedisConnectionFactory initRedisConnectionFactory() {
        if(Objects.nonNull(redisConnectionFactory)){
            return this.redisConnectionFactory;
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        //最大空闲数
        poolConfig.setMaxIdle(30);
        //最大连接数
        poolConfig.setMaxTotal(50);
        //最大等待毫秒数
        poolConfig.setMaxWaitMillis(2000);
        //创建jedis连接工厂
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
        //获取单机的redis配置
        RedisStandaloneConfiguration rsConfig = connectionFactory.getStandaloneConfiguration();
        rsConfig.setHostName("127.0.0.1");
        rsConfig.setPort(6379);
        //rsConfig.setPassword("123456");
        this.redisConnectionFactory = connectionFactory;
        return redisConnectionFactory;
    }*/

    /*@Bean(name = "redisTemplate")
    public RedisTemplate<Object,Object> initRedisTemplate(){
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        //RedisTemplate 会自动初始化 StringRedisSerializer ，所以这里直接获取
        RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
        //设置字符串序列 器，这样 Spr ng 就会把 Redis key 当作字符串处理了
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        //redisTemplate.setConnectionFactory(initRedisConnectionFactory());
        return redisTemplate;
    }*/

    @Bean
    public RedisSerializer jacksonSerializer() {
        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer();

        // 配置ObjectMapper
        try {
            Field field = GenericJackson2JsonRedisSerializer.class.getDeclaredField("mapper");
            field.setAccessible(true);
            ObjectMapper objectMapper = (ObjectMapper) field.get(serializer);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            throw new RuntimeException("Redis序列化器初始化异常");
        }

        return serializer;
    }

    @Bean
    public RedisSerializer stringSerializer(){
        return new StringRedisSerializer();
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate(redisConnectionFactory);
        template.setDefaultSerializer(jacksonSerializer());
        template.setKeySerializer(stringSerializer());
        template.setValueSerializer(jacksonSerializer());
        template.setHashKeySerializer(stringSerializer());
        template.setHashValueSerializer(jacksonSerializer());
        return template;
    }

}
