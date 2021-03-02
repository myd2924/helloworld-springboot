package com.myd.helloworld.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/1/29 16:14
 * @Description:
 */
@Controller
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ResponseBody
    @RequestMapping("/getValueByKey")
    public Object getOne(String key){
        Object o = redisTemplate.opsForValue().get(key);
        manyOperateRedisCallBack();
        manyOperateSessionCallBack();
        return o;
    }

    @ResponseBody
    @RequestMapping("/stringAndHash")
    public Map<String,Object> testStringAndHash(){
        stringRedisTemplate.opsForValue().set("int","1");
        stringRedisTemplate.opsForValue().increment("int",1);
        Map result = new HashMap();
        result.put("int",stringRedisTemplate.opsForValue().get("int"));

        Map<String,String> ha = new HashMap<>();
        ha.put("field1","one");
        ha.put("field2","two");
        stringRedisTemplate.opsForHash().putAll("myHash",ha);
        stringRedisTemplate.opsForHash().put("myHash","field3","three");

        result.put("hash",stringRedisTemplate.opsForHash().entries("myHash"));

        return result;
    }


    @ResponseBody
    @RequestMapping("/operateList")
    public Map<String,Object> testList(){
        //3 2 1
        stringRedisTemplate.opsForList().leftPushAll("list1","1","2","3");
        //7 8 9
        stringRedisTemplate.opsForList().rightPushAll("list2","7","8","9");

        BoundListOperations ops = stringRedisTemplate.boundListOps("list2");
        //9
        Object o1 = ops.rightPop();
        System.out.println(o1);
        //2
        System.out.println(ops.size());

        Map result = new HashMap(1);

        result.put("success",true);

        return result;
    }

    @ResponseBody
    @RequestMapping("/operateSet")
    public Object testSet(){
        stringRedisTemplate.opsForSet().add("set1","2","3","3","4","5","6");
        stringRedisTemplate.opsForSet().add("set2","2","8","9","9","10","5");
        BoundSetOperations set2Ops = stringRedisTemplate.boundSetOps("set2");
        //添加
        set2Ops.add("7");
        //删除
        set2Ops.remove("1");
        //成员
        Set members = set2Ops.members();
        System.out.println(members);
        //成员数
        Long size = set2Ops.size();
        System.out.println(size);
        //交集
        Set set1 = set2Ops.intersect("set1");
        set2Ops.intersectAndStore("set1","inter");
        System.out.println(set1);
        //差集 我有他没有的
        Set set11 = set2Ops.diff("set1");
        set2Ops.diffAndStore("set1","diff");
        System.out.println(set11);
        //并集
        Set set12 = set2Ops.union("set1");
        set2Ops.unionAndStore("set1","union");
        System.out.println(set12);

        return "OK";
    }

    @ResponseBody
    @RequestMapping("/operateZSet")
    public Object testZset(){
        Set<ZSetOperations.TypedTuple<String>> typeSet = new HashSet();
        for(int i=0;i<9;i++){
            double score = i*0.1;
            ZSetOperations.TypedTuple<String> typedTuple = new DefaultTypedTuple<>("value"+i,score);
            typeSet.add(typedTuple);
        }

        stringRedisTemplate.opsForZSet().add("zset1",typeSet);
        BoundZSetOperations<String, String> zsetOps = stringRedisTemplate.boundZSetOps("zset1");
        //插入元素
        zsetOps.add("value10",0.33);
        //获取元素分数
        System.out.println(zsetOps.score("value8"));
        Set<ZSetOperations.TypedTuple<String>> typedTuples1 = zsetOps.rangeWithScores(1, 6);
        System.out.println(typedTuples1);
        Set<ZSetOperations.TypedTuple<String>> typedTuples2 = zsetOps.rangeByScoreWithScores(0.01, 0.66);
        System.out.println(typedTuples2);

        Map result = new HashMap();
        result.put("序号",typedTuples1);
        result.put("分数",typedTuples2);
        return result;
    }

    @ResponseBody
    @RequestMapping("/testPipeline")
    public Object testPipeline(){
        long start = System.currentTimeMillis();

        List list = (List)redisTemplate.executePipelined((RedisOperations op)->{
            for (int i=0;i<100000;i++){
                op.opsForValue().set("pepeline"+i,"value"+i,1000, TimeUnit.SECONDS);
                String value = (String)op.opsForValue().get("pepeline"+i);
                if(i==100000){
                    System.out.println("命令进入队列，所以值为空【"+value+"】");
                }
            }
            return null;
            });
        long end = System.currentTimeMillis();
        System.out.println("耗时"+(end-start)+"毫秒");

        Map map = new HashMap();
        //10w条命令 约4.68s
        map.put("10w",(end-start)+"ms");
        return map;

    }

    @ResponseBody
    @RequestMapping("/redisMsg")
    public Object redisPublish(String msg){
        redisTemplate.convertAndSend("topic1",msg);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/testRedisLua")
    public Object testRedisLua(String key1,String key2,String value1,String value2){
        //定义lua
        String lua = " redis.call('set',KEYS[1],ARGV[1]) \n" +
                " redis.call('set',KEYS[2],ARGV[2]) \n" +
                " local str1 = redis.call('get',KEYS[1]) \n" +
                " local str2 = redis.call('get',KEYS[2]) \n" +
                " if str1 == str2 then \n" +
                " return 1 \n" +
                " end \n" +
                " return 0 \n";
        System.out.println(lua);
        //返回结果为long
        DefaultRedisScript<Long> rs = new DefaultRedisScript<>();
        rs.setScriptText(lua);
        rs.setResultType(Long.class);
        //采用字符化序列
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();
        //定义key参数
        List<String> keyList = new ArrayList<>();
        keyList.add(key1);
        keyList.add(key2);
        //两个序列化参数 一个是参数的 一个是结果集的
        Long result = (Long)redisTemplate.execute(rs, stringRedisSerializer, stringRedisSerializer, keyList, value1, value2);
        Map map = new HashMap();
        map.put("result",result);
        return map;
    }



    private void manyOperateRedisCallBack(){
        redisTemplate.execute((RedisConnection rc)->{
            rc.set("key1".getBytes(),"value1".getBytes());
            rc.hSet("hash".getBytes(),"field1".getBytes(),"hvalue1".getBytes());
            return null;
        });
    }

    private void manyOperateSessionCallBack(){
        redisTemplate.execute((RedisOperations op) -> {
            op.opsForValue().set("myd", "马元丁");
            op.opsForHash().put("family", "dad", "马元丁");
            return null;
        });
    }
}
