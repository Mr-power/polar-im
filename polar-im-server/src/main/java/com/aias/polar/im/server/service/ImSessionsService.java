package com.aias.polar.im.server.service;

import com.aias.polar.im.server.entity.ImSessions;
import java.util.List;

/**
 * (ImSessions)表服务接口
 *
 * @author makejava
 * @since 2020-03-14 11:12:39
 */
public interface ImSessionsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ImSessions queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ImSessions> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @return 实例对象
     */
    ImSessions insert();

    /**
     * 修改数据
     *
     * @param imSessions 实例对象
     * @return 实例对象
     */
    ImSessions update(ImSessions imSessions);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    void changeSession(Integer sessionId);
}