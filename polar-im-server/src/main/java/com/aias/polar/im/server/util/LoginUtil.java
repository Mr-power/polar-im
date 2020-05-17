package com.aias.polar.im.server.util;

import com.aias.polar.constant.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * @author liuhy
 */
public class LoginUtil {
    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttr = channel.attr(Attributes.LOGIN);
        return loginAttr.get() != null;
    }
}
