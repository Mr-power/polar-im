package com.aias.polar.im.server.service;

import com.aias.polar.im.server.entity.ImUserSession;

import java.util.List;

/**
 * (ImUserSession)表服务接口
 *
 * @author makejava
 * @since 2020-03-14 11:18:24
 */
public interface ImUserSessionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ImUserSession queryById(Integer id);

    /**
     * 根据sessionId和fromUserId 查询toUserId
     *
     * @param sessionId
     * @param fromUserId
     * @return
     */
    Integer queryToUserBySessionIdAndUserId(Integer sessionId, Integer fromUserId);

    /**
     * 根据fromUserId和toUserId 查询对应session
     *
     * @param userId
     * @param toUserId
     * @return
     */
    Integer queryByUserIds(Integer userId, Integer toUserId);

    /**
     * 根据fromUserId和toUserId 查询对应session 被关闭的也包含
     *
     * @param userId
     * @return
     */
    List<ImUserSession> queryAllEvenClosedByUserId(Integer userId);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ImUserSession> queryAllByLimit(int offset, int limit);

    /**
     * 根据userId查询全部session
     *
     * @param userId
     * @return
     */
    List<ImUserSession> queryAllByUserId(Integer userId);

    /**
     * 根据sessionId查询所有userSession
     *
     * @param sessionId
     * @return
     */
    List<ImUserSession> queryAllBySessionId(Integer sessionId);

    List<ImUserSession> queryAllEvenClosedBySessionId(Integer sessionId);

    /**
     * 根据sessionId 和 userId 查询 userSession
     *
     * @param sessionId
     * @param userId
     * @return
     */
    ImUserSession queryBySessionIdAndUserId(Integer sessionId, Integer userId);

    ImUserSession queryEvenClosedBySessionIdAndUserId(Integer sessionId, Integer userId);

    /**
     * 新增数据
     *
     * @param imUserSession 实例对象
     * @return 实例对象
     */
    ImUserSession insert(ImUserSession imUserSession);

    /**
     * 修改数据
     *
     * @param imUserSession 实例对象
     * @return 实例对象
     */
    ImUserSession update(ImUserSession imUserSession);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}