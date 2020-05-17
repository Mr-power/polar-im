package com.aias.polar.im.server.service.impl;

import com.aias.polar.im.server.DTO.UserDTO;
import com.aias.polar.im.server.dao.ImUserFriendDao;
import com.aias.polar.im.server.entity.ImUserFriend;
import com.aias.polar.im.server.service.ImUserFriendService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (ImUserFriends)表服务实现类
 *
 * @author makejava
 * @since 2020-03-13 23:09:07
 */
@Service("imUserFriendService")
public class ImUserFriendServiceImpl implements ImUserFriendService {
    @Resource
    private ImUserFriendDao imUserFriendDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ImUserFriend queryById(Integer id) {
        return this.imUserFriendDao.queryById(id);
    }

    @Override
    public void addFriend(Integer userId, Integer friendId) {
        ImUserFriend friend = ImUserFriend.builder().userId(userId).friendId(friendId).status(1).build();
        this.imUserFriendDao.insert(friend);
        ImUserFriend friend1 = ImUserFriend.builder().userId(friendId).friendId(userId).status(1).build();
        this.imUserFriendDao.insert(friend1);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ImUserFriend> queryAllByLimit(int offset, int limit) {
        return this.imUserFriendDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<ImUserFriend> queryAllByUserId(Integer userId) {
        return this.imUserFriendDao.queryAllByUserId(userId);
    }

    @Override
    public List<UserDTO> queryAllValidByUserId(Integer userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("status", 1);
        return this.imUserFriendDao.queryAllFriendInfoByMap(map);
    }

    @Override
    public ImUserFriend queryValidByUserIdAndFriendId(Integer userId, Integer friendId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("friendId", friendId);
        map.put("status", 1);
        return this.imUserFriendDao.queryByMap(map);
    }

    @Override
    public ImUserFriend queryByUserIdAndFriendId(Integer userId, Integer friendId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("friendId", friendId);
        return this.imUserFriendDao.queryByMap(map);
    }

    /**
     * 新增数据
     *
     * @param imUserFriend 实例对象
     * @return 实例对象
     */
    @Override
    public ImUserFriend insert(ImUserFriend imUserFriend) {
        this.imUserFriendDao.insert(imUserFriend);
        return imUserFriend;
    }

    /**
     * 修改数据
     *
     * @param imUserFriend 实例对象
     * @return 实例对象
     */
    @Override
    public ImUserFriend update(ImUserFriend imUserFriend) {
        this.imUserFriendDao.update(imUserFriend);
        return this.queryById(imUserFriend.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.imUserFriendDao.deleteById(id) > 0;
    }
}