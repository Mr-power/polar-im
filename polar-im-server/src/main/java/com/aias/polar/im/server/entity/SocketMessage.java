package com.aias.polar.im.server.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liuhy
 * @since 2020/3/14
 */
@Data
public class SocketMessage implements Serializable {
    private static final long serialVersionUID = -2624369517141835083L;

//    private Integer fromUserId;

    private Integer sessionId;

    private String content;


}
