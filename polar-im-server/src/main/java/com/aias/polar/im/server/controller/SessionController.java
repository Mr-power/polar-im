package com.aias.polar.im.server.controller;

import com.aias.polar.im.server.entity.ImUser;
import com.aias.polar.im.server.entity.ImUserSession;
import com.aias.polar.im.server.service.ImSessionsService;
import com.aias.polar.im.server.service.ImUserSessionService;
import com.aias.polar.im.server.service.StartSessionService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author liuhy
 * @since 2020/3/14
 */
@RequestMapping("/session")
@RestController
public class SessionController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

    @Resource
    private ImSessionsService sessionsService;
    @Resource
    private ImUserSessionService userSessionService;
    @Resource
    private StartSessionService startSessionService;

    @RequestMapping("getSession")
    public ResponseEntity<?> getSession(@RequestHeader("Authorization") String token) {
        // 获取当前用户
        ImUser user = userService.queryByToken(token);
        Map<String, Object> result = super.getUserSessions(user.getId());
        return ResponseEntity.ok(result);
    }

    @RequestMapping("changeSession")
    public ResponseEntity<?> changeSession(Integer sessionId) {
        sessionsService.changeSession(sessionId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("startSession")
    public ResponseEntity<?> startSession(@RequestHeader("Authorization") String token, Integer toUserId) {
        // 获取当前用户
        ImUser user = userService.queryByToken(token);
        // 判断是否存在对话
        Integer sessionId = userSessionService.queryByUserIds(user.getId(), toUserId);
        if (null == sessionId) {
            LOGGER.info("之前不存在会话信息,初始化一个");
            sessionId = startSessionService.startSession(user.getId(), toUserId, "我通过了你的朋友验证请求，现在我们可以开始聊天了");
        }
        Map<String, Object> map = super.getUserSessions(user.getId());
        map.put("success", true);
        // 将刚刚生成的 session 设置为当前 session
        map.put("currentSessionId", sessionId);
        return ResponseEntity.ok().body(map);
    }

    @RequestMapping("closeSession")
    public ResponseEntity<?> closeSession(@RequestHeader("Authorization") String token, Integer sessionId) {
        // 获取当前用户
        ImUser user = userService.queryByToken(token);
        Map<String, Object> map = Maps.newHashMap();
        map.put("success", true);

        // 判断是否存在对话
        ImUserSession targetUserSession = userSessionService.queryBySessionIdAndUserId(sessionId, user.getId());
        if (null == targetUserSession) {
            LOGGER.info("没有查询到会话信息:{},直接返回成功", sessionId);
            // 返回成功
            return ResponseEntity.ok().body(map);
        }
        targetUserSession.setCloseFlag(1);
        // 更新 UserSession
        userSessionService.update(targetUserSession);
        // 返回成功
        return ResponseEntity.ok().body(map);
    }

}
