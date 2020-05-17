package com.aias.polar.common.result;

import lombok.Builder;
import lombok.Data;

/**
 * @author liuhy
 */
@Data
public class ServiceResult<T> {
    private Boolean success;

    private String msg;

    private T data;

    public ServiceResult(Boolean success, String msg) {
        new ServiceResult<>(success, msg, null);
    }

    @Builder
    public ServiceResult(Boolean success, String msg, T data) {
        this.success = success;
        this.msg = msg;
        this.data = data;
    }

}
