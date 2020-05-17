package com.aias.polar.im.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuhy
 */
@Data
@AllArgsConstructor
@Builder
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 1677859057767295585L;

    private Integer id;

    private String username;

    private String avatar;

    private String firstLetter;
}
