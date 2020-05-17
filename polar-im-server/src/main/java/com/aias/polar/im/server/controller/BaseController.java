package com.aias.polar.im.server.controller;

import com.aias.polar.common.utils.ConvertUtils;
import com.aias.polar.im.server.DTO.MessageDTO;
import com.aias.polar.im.server.DTO.SessionDTO;
import com.aias.polar.im.server.DTO.UserDTO;
import com.aias.polar.im.server.entity.ImMessage;
import com.aias.polar.im.server.entity.ImUser;
import com.aias.polar.im.server.entity.ImUserSession;
import com.aias.polar.im.server.service.ImMessageService;
import com.aias.polar.im.server.service.ImUserService;
import com.aias.polar.im.server.service.ImUserSessionService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author liuhy
 * @since 2020/3/14
 */
public class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
    @Resource
    protected ImUserService userService;
    @Resource
    protected ImUserSessionService userSessionService;
    @Resource
    protected ImMessageService messageService;

    protected Map<String, Object> getUserSessions(Integer userId) {

        Map<String, Object> result = Maps.newHashMap();

        // 根据当前登录用户id 获取所有用户相关会话
        List<ImUserSession> sessionList = userSessionService.queryAllByUserId(userId);

        // 获取所有会话列表
//        List<ImSessions> sessionsList = sessionsService.queryAllByUserId(user.getId());
        List<SessionDTO> sessions = Lists.newLinkedList();
        // 根据会话列表查询所有 message
        for (ImUserSession userSession : sessionList) {

//            ImUser toUser = userService.queryById(session.getToUserId());

            // 根据会话信息查到另外一个用户信息
            List<ImUserSession> userSessions = userSessionService.queryAllBySessionId(userSession.getSessionId());

            Optional<ImUserSession> optional =
                    userSessions.stream().filter(s -> !s.getUserId().equals(userId)).findFirst();

            if (!optional.isPresent()) {
                LOGGER.error("根据会话信息:{} 和当前用户Id:{} 没有查到对话用户id", userSession.getSessionId(), userId);
                continue;
            }
            ImUser toUser = userService.queryById(optional.get().getUserId());

            List<ImMessage> messageList = messageService.queryAllBySessionId(userSession.getSessionId());

            /*
            拼装会话对应消息 json格式
             * {
             *         id: 1,
             *         user: {
             *           username: '张三',
             *           avatar: 'https://cdn.v2ex.com/avatar/90e9/1d44/457029_normal.png'
             *         },
             *         messages: [
             *           {
             *             content: '内容',
             *             timeStamp: 时间戳
             *           }
             *         ]
             *       },
             */

            List<MessageDTO> messages =
                    messageList.stream().map(m -> {
                        MessageDTO messageDTO = ConvertUtils.modelToDto(m, MessageDTO.class);
                        if (userId.equals(m.getFromUserId())) {
                            messageDTO.setSelf(true);
                        }
                        return messageDTO;
                    }).collect(Collectors.toList());

            SessionDTO sessionDTO = SessionDTO.builder().id(userSession.getSessionId())
                                              .user(UserDTO.builder().username(toUser.getUsername())
                                                           .avatar(toUser.getAvatar()).build())
                                              .messages(messages)
                                              .build();
            sessions.add(sessionDTO);
        }
        result.put("sessions", JSON.parseArray(JSON.toJSONStringWithDateFormat(sessions, "yyyy-MM-dd HH:mm:ss",
                SerializerFeature.WriteDateUseDateFormat)));
        // 获取时间最新的作为当前选中的session
        result.put("currentSessionId", sessionList.get(0).getSessionId());
        return result;
    }
}
