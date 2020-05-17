package com.aias.polar.im.server.DTO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author liuhy
 * @since 2020/3/14
 */
@Data
public class MessageDTO implements Serializable {
    private static final long serialVersionUID = -2830436988132854999L;

    private String content;

    private Date timeStamp;

    private boolean self;

}
