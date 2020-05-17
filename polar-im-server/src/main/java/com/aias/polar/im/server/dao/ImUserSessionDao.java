package com.aias.polar.im.server.dao;

import com.aias.polar.im.server.entity.ImUserSession;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * (ImUserSession)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-14 11:18:24
 */
public interface ImUserSessionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ImUserSession queryById(Integer id);

    Integer queryToUserIdBySessionIdAndFromUserId(@Param("sessionId") Integer sessionId,
                                                  @Param("fromUserId") Integer fromUserId);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<ImUserSession> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    List<ImUserSession> queryAllByMap(Map<String,Object> map);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param imUserSession 实例对象
     * @return 对象列表
     */
    List<ImUserSession> queryAll(ImUserSession imUserSession);

    /**
     * 新增数据
     *
     * @param imUserSession 实例对象
     * @return 影响行数
     */
    int insert(ImUserSession imUserSession);

    /**
     * 修改数据
     *
     * @param imUserSession 实例对象
     * @return 影响行数
     */
    int update(ImUserSession imUserSession);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}