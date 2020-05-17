package com.aias.polar.im.server.service;

/**
 * @author liuhy
 * @since 2020/4/9
 */
public interface StartSessionService {

    Integer startSession(Integer myUserId, Integer toUserId, String firstMessage);
}
