package com.aias.polar.im.server.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuhy
 * @since 2020/3/14
 */
@Data
@AllArgsConstructor
@Builder
public class SessionDTO implements Serializable {
    private static final long serialVersionUID = -100083198619347585L;

    private Integer id;

    private UserDTO user;

    private List<MessageDTO> messages;

}
