package com.aias.polar.im.server.util;

import com.aias.polar.constant.Attributes;
import com.google.common.collect.Maps;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;

/**
 * @author liuhy
 */
public class UserChannelUtil {
    // userId -> channel 的映射
    private static final Map<Integer, Channel> userIdChannelMap = Maps.newConcurrentMap();
    private static final Map<Integer, ChannelGroup> channelGroupMap = Maps.newConcurrentMap();

    public static void bindUser(Integer userId, Channel channel) {
        userIdChannelMap.put(userId, channel);
        channel.attr(Attributes.USER).set(userId);
    }

    public static void unBindUser(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getUser(channel));
            channel.attr(Attributes.USER).set(null);
        }

    }

    public static Integer getUser(Channel channel) {

        return channel.attr(Attributes.USER).get();
    }

    public static Channel getChannel(Integer userId) {

        return userIdChannelMap.get(userId);
    }

    public static void addChannelGroup(Integer groupId, ChannelGroup channelGroup) {
        channelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(Integer groupId) {
        return channelGroupMap.get(groupId);
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.USER);
    }

}
