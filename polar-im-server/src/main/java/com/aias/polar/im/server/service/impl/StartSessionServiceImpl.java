package com.aias.polar.im.server.service.impl;

import com.aias.polar.im.server.entity.ImSessions;
import com.aias.polar.im.server.entity.ImUserSession;
import com.aias.polar.im.server.service.ImMessageService;
import com.aias.polar.im.server.service.ImSessionsService;
import com.aias.polar.im.server.service.ImUserSessionService;
import com.aias.polar.im.server.service.StartSessionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuhy
 * @since 2020/4/9
 */
@Service
public class StartSessionServiceImpl implements StartSessionService {

    @Resource
    private ImSessionsService sessionsService;
    @Resource
    private ImUserSessionService userSessionService;
    @Resource
    private ImMessageService messageService;

    @Override
    public Integer startSession(Integer myUserId, Integer toUserId, String firstMessage) {
        ImSessions session = sessionsService.insert();
        // 创建 userSession
        ImUserSession userSession0 =
                ImUserSession.builder().sessionId(session.getId()).userId(myUserId).build();
        userSessionService.insert(userSession0);
        ImUserSession userSession =
                ImUserSession.builder().sessionId(session.getId()).userId(toUserId).build();
        userSessionService.insert(userSession);
        // 返回 我通过了你的朋友验证请求，现在我们可以开始聊天了/欢迎
        messageService.addTextMessage(session.getId(), toUserId, firstMessage);
        return session.getId();
    }
}
