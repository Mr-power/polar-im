package com.aias.polar.common.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.annotation.PostConstruct;

/**
 * @author liuhy
 * @ClassName JsonMapper
 * @date 2020/1/29 15:22
 */
public class JsonMapper extends ObjectMapper {
    private static final long serialVersionUID = -1406232567779495253L;

    @PostConstruct
    public void init() {
        // 转换为格式化的json
        this.enable(SerializationFeature.INDENT_OUTPUT);
        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
