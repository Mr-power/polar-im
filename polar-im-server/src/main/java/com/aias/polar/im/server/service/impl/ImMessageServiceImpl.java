package com.aias.polar.im.server.service.impl;

import com.aias.polar.im.server.dao.ImMessageDao;
import com.aias.polar.im.server.entity.ImMessage;
import com.aias.polar.im.server.entity.SocketMessage;
import com.aias.polar.im.server.service.ImMessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ImMessage)表服务实现类
 *
 * @author makejava
 * @since 2020-03-13 23:36:18
 */
@Service("imMessageService")
public class ImMessageServiceImpl implements ImMessageService {
    @Resource
    private ImMessageDao imMessageDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ImMessage queryById(Integer id) {
        return this.imMessageDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ImMessage> queryAllByLimit(int offset, int limit) {
        return this.imMessageDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<ImMessage> queryAllBySessionId(Integer sessionId) {
        return imMessageDao.queryAllBySessionId(sessionId);
    }

    /**
     * 新增数据
     *
     * @param imMessage 实例对象
     * @return 实例对象
     */
    @Override
    public ImMessage insert(ImMessage imMessage) {
        this.imMessageDao.insert(imMessage);
        return imMessage;
    }

    /**
     * 修改数据
     *
     * @param imMessage 实例对象
     * @return 实例对象
     */
    @Override
    public ImMessage update(ImMessage imMessage) {
        this.imMessageDao.update(imMessage);
        return this.queryById(imMessage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.imMessageDao.deleteById(id) > 0;
    }

    @Override
    public void addTextMessage(Integer fromUserId, SocketMessage socketMessage) {
        ImMessage message = new ImMessage();
        BeanUtils.copyProperties(socketMessage, message);
        message.setType(1);
        message.setFromUserId(fromUserId);
        this.insert(message);
    }

    @Override
    public void addTextMessage(Integer sessionId, Integer fromUserId, String content) {
        ImMessage message = new ImMessage();
        message.setType(1);
        message.setFromUserId(fromUserId);
        message.setSessionId(sessionId);
        message.setContent(content);
        this.insert(message);
    }
}