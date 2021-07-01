package com.myd.helloworld.jms.consumer.resolver.support;

import com.myd.helloworld.entity.Message;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:46
 * @Description:
 * 实现说明:
 * 1. 按业务ID进行切片，该项目目前写死按chainMasterId进行切片。
 * 任务调度时会保证一个业务ID(chainMasterId)下相关的所有消息任务按接收到的时间依次执行。
 * 2. {@link #TODO_SLICE_ID_KEY} 存放待调度处理的chainMasterId，为SortedSet结构，key: chainMasterId, score: chainMasterId申请调度入库的当前时间戳;
 * 入口见: {@link #join(List)} 及 {@link #joinIfAbsent(String)}
 * 3. {@link #DOING_SLICE_ID_KEY} 存放正在调度处理中的chainMasterId，同样为SortedSet结构. 用于保证集群中同一个chainMasterId正在处理时，新加入调度的任务不会被其他线程处理;
 * 4. 每个待处理的chainMasterId单独存放待处理的任务ID和处理中的任务ID。
 */
@Slf4j
@Component
public class DispatchTaskPool implements DispatchContants{

    @Resource
    @Qualifier("redisTemplate")
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 加入调度工单
     * <p>
     * 1. 多线程抢占任务时，可以按业务编号取mod再切片，减小锁粒度
     * 2. 守护线程定时检测因特殊异常情况(进程飞掉,redis网络中断宕机,断电..)导致长时间正在处理的业务编号
     */
    public void join(final List<Message> messages){
        CollectionUtils.emptyIfNull(messages).stream().
                collect(Collectors.groupingBy(Message::getSliceId))
                .forEach(this::join4EachSliceId);
    }

    private void join4EachSliceId(final String sliceId, final List<Message> messages) {
        final String todoKey = this.todoKeyOfSliceId(sliceId);
        redisTemplate.opsForList().leftPushAll(todoKey,messages.stream().map(Message::getId).toArray(String[]::new));
        //考虑给个过期时间
        messages.forEach(message -> redisTemplate.opsForValue().set(this.messageIdKey(message.getId()),message));
        this.joinIfAbsent(sliceId);
    }

    /**
     * 加入到待处理工单。
     * SortedSet结构，key: chainMasterId, score: 首次记录时间戳
     * @param sliceId
     */
    private void joinIfAbsent(final String sliceId){
        redisTemplate.execute(
                new DefaultRedisScript<>(LUA_ADD_IF_ABSENT,String.class),
                Collections.singletonList(TODO_SLICE_ID_KEY),
                sliceId,
                Instant.now().toEpochMilli());
    }

    /**
     * 获取下一批待处理工单
     * 按chainMasterId切片获取工单列表。
     * 一次最多获取{@link #MAX_TASK_SIZE_EACH_TIME}条待处理工单，防止长时间占用切片时间，其他chainMasterId的任务无法分配到处理任务
     */
    public DispatchTask next(){
        final String sliceId = this.backupThenNextTodoSliceId();
        if(StringUtils.isNotEmpty(sliceId)){
            final String todoKeyOfSliceId = this.todoKeyOfSliceId(sliceId);
            final String doingKeyOfSliceId = this.doingKeyOfSliceId(sliceId);
            final Integer size = Optional.ofNullable(redisTemplate.opsForList().size(todoKeyOfSliceId)).map(Long::intValue).orElse(0);
            final ArrayList<String> messageIds = new ArrayList<>(Math.min(size, MAX_TASK_SIZE_EACH_TIME));
            int i=0;
            String messageId;
            do{
                messageId = (String)redisTemplate.opsForList().rightPopAndLeftPush(todoKeyOfSliceId,doingKeyOfSliceId);
                if(StringUtils.isNotEmpty(messageId)){
                    messageIds.add(messageId);
                }
                i++;
            } while(i<MAX_TASK_SIZE_EACH_TIME && StringUtils.isNotEmpty(messageId));
            if(CollectionUtils.isEmpty(messageIds)){
                this.asComplete(DispatchTask.of(sliceId, Collections.emptyList()));
            }
            return DispatchTask.of(sliceId,Collections.unmodifiableList(messageIds));
        }
        return null;
    }

    private String backupThenNextTodoSliceId() {
        return redisTemplate.execute(new DefaultRedisScript<>(LUA_ZPOP_AND_PUSH_IF_ABSENT, String.class),
                Arrays.asList(TODO_SLICE_ID_KEY, DOING_SLICE_ID_KEY),
                Instant.now().toEpochMilli(),
                DEFAULT_TTL);
    }

    @SuppressWarnings("WeakerAccess")
    public Message getMessageById(final String messageId) {
        return (Message) redisTemplate.opsForValue().get(this.messageIdKey(messageId));
    }

    /**
     * 单条消息处理失败
     */
    @SuppressWarnings("WeakerAccess")
    public void asFailed(final Message message) {
        redisTemplate.opsForList().rightPush(FAILED_TASK_ID_KEY, message.getId());
    }

    /**
     * 单条消息处理成功
     */
    @SuppressWarnings("WeakerAccess")
    public void asComplete(final Message message) {
        redisTemplate.opsForList().remove(this.doingKeyOfSliceId(message.getChainMasterId()), 1, message.getId());
        redisTemplate.delete(this.messageIdKey(message.getId()));
    }

    /**
     * 整个批次处理成功
     */
    @SuppressWarnings("WeakerAccess")
    public void asComplete(final DispatchTask task) {
        redisTemplate.opsForZSet().remove(DOING_SLICE_ID_KEY, task.getSliceId());
        this.tryReJoin(task.getSliceId());
    }

    /**
     * 处理过程结果，还有新的任务进来，则重新申请加入调度
     */
    private void tryReJoin(final String sliceId) {
        if (redisTemplate.hasKey(this.todoKeyOfSliceId(sliceId))) {
            this.joinIfAbsent(sliceId);
        }
    }

}
