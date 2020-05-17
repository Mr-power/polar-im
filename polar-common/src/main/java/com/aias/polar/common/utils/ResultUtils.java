package com.aias.polar.common.utils;

import com.aias.polar.common.result.Result;
import com.aias.polar.common.result.Status;

/**
 * @author liuhy
 */
public class ResultUtils<T> {

    public static <T> Result<T> success() {
        return Result.<T>builder().code(Status.SUCCESS.getCode()).msg(Status.SUCCESS.getMsg()).build();
    }

    public static <T> Result<T> success(T data) {
        return Result.<T>builder().code(Status.SUCCESS.getCode()).msg(Status.SUCCESS.getMsg()).data(data).build();
    }

    public static <T> Result<T> fail() {
        return Result.<T>builder().code(Status.FAIL.getCode()).msg(Status.FAIL.getMsg()).build();
    }

    public static <T> Result<T> fail(String msg) {
        return Result.<T>builder().code(Status.FAIL.getCode()).msg(msg).build();
    }

    public static <T> Result<T> fail(Exception e) {
        return Result.<T>builder().code(Status.FAIL.getCode()).msg(e.getMessage()).build();
    }

}
