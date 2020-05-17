package com.aias.polar.im.server.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;

/**
 * (ImMessage)实体类
 *
 * @author makejava
 * @since 2020-03-13 23:36:18
 */
@Data
public class ImMessage implements Serializable {
    private static final long serialVersionUID = -52797793457788337L;
    /**
    * 自增主键
    */
    private Integer id;
    /**
    * 消息类型
    */
    private Integer type;
    /**
    * 发送方userId
    */
    private Integer fromUserId;
    /**
     * 会话id
     */
    private Integer sessionId;
    /**
    * 时间戳
    */
    private Date timeStamp;
    /**
    * 消息内容
    */
    private String content;


}