package com.aias.polar.im.server.service;

import com.aias.polar.im.server.entity.ImMessage;
import com.aias.polar.im.server.entity.SocketMessage;

import java.util.List;

/**
 * (ImMessage)表服务接口
 *
 * @author makejava
 * @since 2020-03-13 23:36:18
 */
public interface ImMessageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ImMessage queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ImMessage> queryAllByLimit(int offset, int limit);

    List<ImMessage> queryAllBySessionId(Integer sessionId);

    /**
     * 新增数据
     *
     * @param imMessage 实例对象
     * @return 实例对象
     */
    ImMessage insert(ImMessage imMessage);

    /**
     * 修改数据
     *
     * @param imMessage 实例对象
     * @return 实例对象
     */
    ImMessage update(ImMessage imMessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void addTextMessage(Integer fromUserId, SocketMessage socketMessage);
    void addTextMessage(Integer sessionId, Integer fromUserId, String content);

}