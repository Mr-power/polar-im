package com.aias.polar.im.server.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;

/**
 * (ImSessions)实体类
 *
 * @author makejava
 * @since 2020-03-14 11:12:36
 */
@Data
public class ImSessions implements Serializable {
    private static final long serialVersionUID = 266908679948060650L;
    /**
    * 自增主键
    */
    private Integer id;
    /**
    * 更新时间
    */
    private Date updateTime;


}