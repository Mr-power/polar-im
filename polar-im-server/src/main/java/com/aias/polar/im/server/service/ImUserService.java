package com.aias.polar.im.server.service;

import com.aias.polar.im.server.entity.ImUser;
import java.util.List;

/**
 * (ImUser)表服务接口
 *
 * @author makejava
 * @since 2020-03-12 23:33:32
 */
public interface ImUserService {


    ImUser queryByToken(String token);

    ImUser queryByName(String name);

    ImUser queryByAccount(String account);


    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ImUser queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ImUser> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param imUser 实例对象
     * @return 实例对象
     */
    ImUser insert(ImUser imUser);

    /**
     * 修改数据
     *
     * @param imUser 实例对象
     * @return 实例对象
     */
    ImUser update(ImUser imUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Object id);

}