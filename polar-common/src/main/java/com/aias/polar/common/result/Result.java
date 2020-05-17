package com.aias.polar.common.result;

import com.aias.polar.common.json.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 6093068966793503086L;
    /**
     * 状态码
     */
    protected Integer code;
    /**
     * 状态信息
     */
    protected String msg;
    /**
     * 时间戳
     */
    protected Long timeStamp = System.currentTimeMillis();

    private T data;

    @Builder
    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        JsonMapper mapper = new JsonMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
