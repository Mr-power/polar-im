package com.aias.polar.im.server.dao;

import com.aias.polar.im.server.DTO.UserDTO;
import com.aias.polar.im.server.entity.ImUserFriend;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * (ImUserFriends)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-13 23:09:07
 */
public interface ImUserFriendDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ImUserFriend queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ImUserFriend> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<ImUserFriend> queryAllByUserId(Integer userId);

    List<UserDTO> queryAllFriendInfoByMap(Map<String,Object> map);

    ImUserFriend queryByMap(Map<String,Object> map);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param imUserFriend 实例对象
     * @return 对象列表
     */
    List<ImUserFriend> queryAll(ImUserFriend imUserFriend);

    /**
     * 新增数据
     *
     * @param imUserFriend 实例对象
     * @return 影响行数
     */
    int insert(ImUserFriend imUserFriend);

    /**
     * 修改数据
     *
     * @param imUserFriend 实例对象
     * @return 影响行数
     */
    int update(ImUserFriend imUserFriend);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}