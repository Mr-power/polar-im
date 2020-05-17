package com.aias.polar.im.server.service;

import com.aias.polar.im.server.DTO.UserDTO;
import com.aias.polar.im.server.entity.ImUserFriend;

import java.util.List;

/**
 * (ImUserFriends)表服务接口
 *
 * @author makejava
 * @since 2020-03-13 23:09:07
 */
public interface ImUserFriendService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ImUserFriend queryById(Integer id);

    void addFriend(Integer userId, Integer friendId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ImUserFriend> queryAllByLimit(int offset, int limit);

    List<ImUserFriend> queryAllByUserId(Integer userId);

    List<UserDTO> queryAllValidByUserId(Integer userId);

    ImUserFriend queryValidByUserIdAndFriendId(Integer userId, Integer friendId);

    ImUserFriend queryByUserIdAndFriendId(Integer userId, Integer friendId);

    /**
     * 新增数据
     *
     * @param imUserFriend 实例对象
     * @return 实例对象
     */
    ImUserFriend insert(ImUserFriend imUserFriend);

    /**
     * 修改数据
     *
     * @param imUserFriend 实例对象
     * @return 实例对象
     */
    ImUserFriend update(ImUserFriend imUserFriend);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}