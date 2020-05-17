package com.aias.polar.im.server.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * (ImUserFriend)实体类
 *
 * @author makejava
 * @since 2020-03-13 23:09:07
 */
@Data
@AllArgsConstructor
@Builder
public class ImUserFriend implements Serializable {
    private static final long serialVersionUID = 616325423880666157L;
    /**
    *  自增主键
    */
    private Integer id;
    
    private Integer userId;
    
    private Integer friendId;
    
    private Integer status;
    
    private Date createTime;
    
    private Date updateTime;


}