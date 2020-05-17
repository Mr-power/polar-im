package com.aias.polar.im.server.controller;

import com.aias.polar.constant.Constants;
import com.aias.polar.im.server.DTO.UserDTO;
import com.aias.polar.im.server.entity.ImUser;
import com.aias.polar.im.server.entity.ImUserFriend;
import com.aias.polar.im.server.service.ImUserFriendService;
import com.aias.polar.im.server.service.ImUserService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author liuhy
 * @since 2020/3/15
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Resource
    private ImUserService userService;

    @Resource
    private ImUserFriendService friendService;

    @RequestMapping("/update")
    public ResponseEntity<?> updateCurrentUserInfo(@RequestHeader("Authorization") String token, String username
            , String avatar) {
        // 获取当前登录用户信息
        ImUser user = userService.queryByToken(token);
        user.setUsername(username);
        user.setAvatar(avatar);
        userService.update(user);

        Map<String, Object> map = Maps.newHashMap();

        map.put("success", true);
        return ResponseEntity.ok(map);
    }

    @RequestMapping("/addFriend")
    public ResponseEntity<?> addFriend(@RequestHeader("Authorization") String token, Integer friendId) {
        Map<String, Object> map = Maps.newHashMap();
        // 获取当前登录用户信息
        ImUser user = userService.queryByToken(token);
        map.put("success", false);
        if (friendId.equals(user.getId())) {
            map.put("msg", "不能把自己添加到通讯录");
            return ResponseEntity.ok(map);
        }
        // 判断是否存在状态为有效的好友关系
        ImUserFriend userFriend = friendService.queryByUserIdAndFriendId(user.getId(), friendId);
        if (null != userFriend && Constants.UserFriendStatus.VALID.equals(userFriend.getStatus())) {
            map.put("msg", "不能把自己添加到通讯录");
            return ResponseEntity.ok(map);
        }

        friendService.addFriend(user.getId(), friendId);
        map.put("success", true);
        // 获取用户未删除的好友列表
        List<UserDTO> friendList = friendService.queryAllValidByUserId(user.getId());

        map.put("friendList", friendList);
        return ResponseEntity.ok(map);
    }

    @RequestMapping("/friend")
    public ResponseEntity<?> queryFriendInfo(Integer friendId) {
        Map<String, Object> map = Maps.newHashMap();
        ImUser user = userService.queryById(friendId);
        if (null == user) {
            map.put("success", false);
            return ResponseEntity.ok(map);
        }
        UserDTO userDTO = UserDTO.builder().username(user.getUsername()).id(friendId).avatar(user.getAvatar()).build();
        map.put("isFriend",false);
        // 判断是否存在状态为有效的好友关系
        ImUserFriend userFriend = friendService.queryByUserIdAndFriendId(user.getId(), friendId);
        if (null != userFriend &&
                Constants.UserFriendStatus.VALID.equals(userFriend.getStatus())) {
            map.put("isFriend",true);
        }
        map.put("success", true);
        map.put("friend", userDTO);
        return ResponseEntity.ok(map);
    }

    @RequestMapping("/account")
    public ResponseEntity<?> queryFriendInfoByAccount(String account) {
        Map<String, Object> map = Maps.newHashMap();
        ImUser user = userService.queryByAccount(account);
        if (null == user) {
            map.put("success", false);
            return ResponseEntity.ok(map);
        }
        UserDTO userDTO =
                UserDTO.builder().username(user.getUsername()).id(user.getId()).avatar(user.getAvatar()).build();

        map.put("success", true);
        map.put("user", userDTO);
        return ResponseEntity.ok(map);
    }
//
//    @RequestMapping("/init")
//    public ResponseEntity<?> initUserInfo(@RequestHeader("Authorization") String token){
//
//    }

    @RequestMapping("/password")
    public ResponseEntity<?> changePassword(@RequestHeader("Authorization") String token, String oldPassword
            , String newPassword) {
        Map<String, Object> map = Maps.newHashMap();
        // 获取当前登录用户信息
        ImUser user = userService.queryByToken(token);

        // 比较传入的旧密码是否正确
        if (!user.getPassword().equals(oldPassword)) {
            map.put("success", false);
            return ResponseEntity.ok(map);
        }
        user.setPassword(newPassword);
        userService.update(user);

        map.put("success", true);
        return ResponseEntity.ok(map);
    }

}
