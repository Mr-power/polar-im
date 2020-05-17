package com.aias.polar.im.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * (ImUserSession)实体类
 *
 * @author makejava
 * @since 2020-03-14 11:18:24
 */
@Data
@Builder
@AllArgsConstructor
public class ImUserSession implements Serializable {
    private static final long serialVersionUID = -19808268301499226L;
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 会话id
     */
    private Integer sessionId;
    /**
     * 用户Id
     */
    private Integer userId;
    /**
     * 是否关闭 0-否 1-是
     */
    private Integer closeFlag;
    /**
     * 是否置顶  0-否 1-是
     */
    private Integer topFlag;

}