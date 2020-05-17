package com.aias.polar.constant;

import io.netty.util.AttributeKey;

/**
 * @author liuhy
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    AttributeKey<Integer> USER = AttributeKey.newInstance("user");

}
