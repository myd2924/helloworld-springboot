package com.myd.helloworld.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mayuanding@qianmi.com">OF3787-马元丁</a>
 * @version 0.1.0
 * @Date:2021/6/30 17:08
 * @Description: 消息实体
 */
@javax.persistence.Entity
@Table(name = "PC_MESSAGE")
@Slf4j
@Data
@Builder(toBuilder = true, builderClassName = "MessageBuilder")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Message implements Serializable{
    private static final long serialVersionUID = 3218002418305153247L;
    /**
     * 主键ID。非空
     */
    @Id
    @Column(name = "id")
    private              String        id;
    /**
     * 消息ID。非空
     */
    @Column(name = "message_id")
    private              String        messageId;
    /**
     * 消息topic或者queue队列名称。非空
     */
    @Column(name = "message_name", updatable = false)
    private              String        messageName;
    /**
     * 发送方应用名称，非必选项
     */
    @Column(name = "sender_app")
    private              String        senderApp;
    /**
     * 发送方IP地址，非必选项
     */
    @Column(name = "sender_ip")
    private              String        senderIp;
    /**
     * 发送方发送时间，非必选项
     */
    @Column(name = "sender_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date senderTime;
    /**
     * 消息报文，非空
     */
    @Column(name = "message", updatable = false)
    private              String        message;
    /**
     * 消息业务类别，非必选项
     *//*
    @Column(name = "business_type")
    @Convert(converter = BusinessTypeConverter.class)
    private              BusinessType  businessType;*/
    /**
     * 消息业务所属站长编号，非空
     */
    @Column(name = "cmid")
    private              String        chainMasterId;
    /**
     * 任务切片ID，为空默认按chainMasterId切片
     */
    @Column(name = "SLICE_ID")
    private              String        sliceId;
    /**
     * 业务ID类型
     *//*
    @Column(name = "item_id_type")
    @Convert(converter = ItemIdTypeConverter.class)
    private              ItemIdType    itemIdType;*/
    /**
     * 业务ID编号列表
     */
    @Column(name = "item_id")
    private              String        itemId;
   /* *//**
     * 更新类型: add/update/delete
     *//*
    @Column(name = "op_type")
    @Convert(converter = OperateTypeConverter.class)
    private              OperateType   opType;*/
    /**
     * 消息处理状态。0未处理，1处理中，2处理完成，9处理失败。非空
     */
   /* @Builder.Default
    @Column(name = "status")
    @Convert(converter = MessageStatusConverter.class)
    private              MessageStatus status           = MessageStatus.INITIAL;*/
    /**
     * 处理耗时(毫秒)
     */
    @Builder.Default
    @Column(name = "cost")
    private              long          cost             = -1;
    /**
     * 消息记录（接收）新增时间。非空
     */
    @Column(name = "add_time")
    @Temporal(TemporalType.TIMESTAMP)
    private              Date          addTime;
    /**
     * 消息记录（状态）变更时间。非空
     */
    @Column(name = "modify_time")
    @Temporal(TemporalType.TIMESTAMP)
    private              Date          updateTime;
    /**
     * 处理失败时的错误编码，非必选项
     */
    @Column(name = "error_code")
    private              String        errorCode;
    /**
     * 处理失败时的错误信息，非必选项
     */
    @Column(name = "error_message")
    private              String        errorMessage;

    public String getSliceId() {
        return StringUtils.defaultIfBlank(sliceId, chainMasterId);
    }

    public void asComplete() {
        this.setUpdateTime(new Date());
        //this.setStatus(MessageStatus.PROCESS_SUCCESS);
    }

    public void asFailed(final Exception e) {
        this.setUpdateTime(new Date());
        //this.setStatus(MessageStatus.PROCESS_FAILED);
        this.setErrorMessage(e.getMessage());
    }

    public Map<?, ?> parseMessageAsMap() {
        try {
            Object object = JSON.parse(message);
            if (object instanceof JSONObject) {
                return ((JSONObject) object).toJavaObject(Map.class);
            }
        } catch (Exception e) {
            log.error("Failed to parse messageBody to Map. message={}", message, e);
        }

        return Collections.emptyMap();
    }

    public List<Map> parseMessageAsList() {
        try {
            Object object = JSON.parse(message);
            if (object instanceof JSONArray) {
                return ((JSONArray) object).toJavaList(Map.class);
            } else if (object instanceof JSONObject) {
                return Collections.singletonList(((JSONObject) object).toJavaObject(Map.class));
            }
        } catch (Exception e) {
            log.error("Failed to parse messageBody to List. message={}", message, e);
        }

        return Collections.emptyList();
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    @PostPersist
    public void subMessage() {
        // 最大存储
        if (StringUtils.length(message) >= 1000) {
            log.info("message[{}]内容过大，被截取内容={}", this.getId(), message);
            this.setMessage(StringUtils.substring(message, 0, 1000));
        }

        if (StringUtils.length(errorMessage) >= 1000) {
            log.info("errorMessage[{}]内容过大，被截取内容={}", this.getId(), errorMessage);
            this.setErrorMessage(StringUtils.substring(errorMessage, 0, 1000));
        }
    }
}
