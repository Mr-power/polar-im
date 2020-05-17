package com.aias.polar.im.server.service.impl;

import com.aias.polar.im.server.dao.ImSessionsDao;
import com.aias.polar.im.server.entity.ImSessions;
import com.aias.polar.im.server.service.ImSessionsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ImSessions)表服务实现类
 *
 * @author makejava
 * @since 2020-03-14 11:12:39
 */
@Service("imSessionsService")
public class ImSessionsServiceImpl implements ImSessionsService {
    @Resource
    private ImSessionsDao imSessionsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ImSessions queryById(Integer id) {
        return this.imSessionsDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ImSessions> queryAllByLimit(int offset, int limit) {
        return this.imSessionsDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @return 实例对象
     */
    @Override
    public ImSessions insert() {
        ImSessions imSessions = new ImSessions();
        this.imSessionsDao.insert(imSessions);
        return imSessions;
    }

    /**
     * 修改数据
     *
     * @param imSessions 实例对象
     * @return 实例对象
     */
    @Override
    public ImSessions update(ImSessions imSessions) {
        this.imSessionsDao.update(imSessions);
        return this.queryById(imSessions.getId());
    }

    @Override
    public void changeSession(Integer sessionId) {
        imSessionsDao.updateSessionSetNow(sessionId);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.imSessionsDao.deleteById(id) > 0;
    }
}