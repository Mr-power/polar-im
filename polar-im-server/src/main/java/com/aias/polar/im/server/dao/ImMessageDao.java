package com.aias.polar.im.server.dao;

import com.aias.polar.im.server.entity.ImMessage;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (ImMessage)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-13 23:36:18
 */
public interface ImMessageDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ImMessage queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ImMessage> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<ImMessage> queryAllBySessionId(Integer sessionId);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param imMessage 实例对象
     * @return 对象列表
     */
    List<ImMessage> queryAll(ImMessage imMessage);

    /**
     * 新增数据
     *
     * @param imMessage 实例对象
     * @return 影响行数
     */
    int insert(ImMessage imMessage);

    /**
     * 修改数据
     *
     * @param imMessage 实例对象
     * @return 影响行数
     */
    int update(ImMessage imMessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}