package com.aias.polar.im.server.controller;

import com.aias.polar.common.utils.ConvertUtils;
import com.aias.polar.im.server.DTO.MessageDTO;
import com.aias.polar.im.server.DTO.SessionDTO;
import com.aias.polar.im.server.DTO.UserDTO;
import com.aias.polar.im.server.entity.ImMessage;
import com.aias.polar.im.server.entity.ImUser;
import com.aias.polar.im.server.entity.ImUserSession;
import com.aias.polar.im.server.service.IAuthService;
import com.aias.polar.im.server.service.ImMessageService;
import com.aias.polar.im.server.service.ImSessionsService;
import com.aias.polar.im.server.service.ImUserFriendService;
import com.aias.polar.im.server.service.ImUserService;
import com.aias.polar.im.server.service.ImUserSessionService;
import com.aias.polar.jwt.utils.JwtUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author liuhy
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private IAuthService IAuthService;

    @Resource
    private ImUserService userService;
    @Resource
    private ImUserFriendService userFriendsService;
    @Resource
    private ImUserSessionService userSessionService;

    @Resource
    private ImSessionsService sessionsService;
    @Resource
    private ImMessageService messageService;

    @Resource
    private JwtUtils jwtUtils;

    @RequestMapping("/check")
    public ResponseEntity<?> check(String account) {
        // 查询账号是否已经存在
        ImUser user = userService.queryByAccount(account);
        Map<String, Object> result = Maps.newHashMap();
        if (null != user) {
            result.put("success", false);
            result.put("msg", "账号已存在");
            return ResponseEntity.ok(result);
        }
        result.put("success", true);
        return ResponseEntity.ok(result);
    }

    @RequestMapping("/register")
    public ResponseEntity<?> register(String username, String account, String password) {
        // 获取当前登录用户信息
        // 截取用户名 获取首字母
        String firstLetter = username.substring(0, 1);
        ImUser newUser = ImUser.builder().username(username)
                               .account(account)
                               .firstLetter(firstLetter)
                               .password(password).build();
        ImUser user = userService.insert(newUser);
        welcome(user.getId());
        Map<String, Object> map = Maps.newHashMap();
        map.put("success", true);
        return ResponseEntity.ok(map);
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(String account,
                                   String password) {
        Map<String, Object> result = Maps.newHashMap();
        boolean checkResult = IAuthService.checkLogin(account, password);
        if (!checkResult) {
            result.put("msg", "用户名密码校验失败");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
        // jwt 生成 token
        String token = jwtUtils.createJWT(account);
        result.put("token", token);

        // 获取用户信息
        ImUser user = userService.queryByAccount(account);
        result.put("id", user.getId());
        result.put("avatar", user.getAvatar());
        result.put("username", user.getUsername());
        result.put("firstLetter", user.getFirstLetter());

        // 根据当前登录用户id 获取所有用户相关会话
        List<ImUserSession> sessionList = userSessionService.queryAllByUserId(user.getId());

        // 获取所有会话列表
        List<SessionDTO> sessions = Lists.newLinkedList();
        // 根据会话列表查询所有 message
        for (ImUserSession userSession : sessionList) {

            // 根据会话信息查到另外一个用户信息
            List<ImUserSession> userSessions = userSessionService.queryAllBySessionId(userSession.getSessionId());

            Optional<ImUserSession> optional =
                    userSessions.stream().filter(s -> !s.getUserId().equals(user.getId())).findFirst();

            if (!optional.isPresent()) {
                LOGGER.error("根据会话信息:{} 和当前用户Id:{} 没有查到对话用户id", userSession.getId(), user.getId());
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
                        if (user.getId().equals(m.getFromUserId())) {
                            messageDTO.setSelf(true);
                        }
                        return messageDTO;
                    }).collect(Collectors.toList());

            SessionDTO sessionDTO = SessionDTO.builder().id(userSession.getSessionId())
                                              .user(UserDTO.builder().username(toUser.getUsername())
                                                           .firstLetter(toUser.getFirstLetter())
                                                           .avatar(toUser.getAvatar()).build())
                                              .messages(messages)
                                              .build();
            sessions.add(sessionDTO);
        }
        // 获取用户未删除的好友列表
        List<UserDTO> friendList = userFriendsService.queryAllValidByUserId(user.getId());

        result.put("friendList", friendList);
        result.put("sessions", JSON.parseArray(JSON.toJSONStringWithDateFormat(sessions, "yyyy-MM-dd HH:mm:ss",
                SerializerFeature.WriteDateUseDateFormat)));
        // 获取时间最新的作为当前选中的session
        result.put("currentSessionId", sessionList.get(0).getSessionId());
        return ResponseEntity.ok(result);
    }

    private void welcome(Integer userId) {
//        ImSessions sessions = sessionsService.insert();
//        ImUserSession userSession0 =
//                ImUserSession.builder().sessionId(sessions.getId()).userId(0).build();
//        userSessionService.insert(userSession0);
//        ImUserSession userSession =
//                ImUserSession.builder().sessionId(sessions.getId()).userId(userId).build();
//        userSessionService.insert(userSession);
//        messageService.addTextMessage(sessions.getId(), 0, "欢迎!");
        userSessionService.startSession(userId, 0, "欢迎");
    }
}
