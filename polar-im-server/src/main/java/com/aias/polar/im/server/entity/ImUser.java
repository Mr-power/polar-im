package com.aias.polar.im.server.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * (ImUser)实体类
 *
 * @author makejava
 * @since 2020-03-13 09:59:30
 */
@Data
@AllArgsConstructor
@Builder
public class ImUser implements Serializable {
    private static final long serialVersionUID = -60419856042939281L;

    private Integer id;

    private String account;

    private String username;

    private String password;

    private String avatar;

    private String firstLetter;
}