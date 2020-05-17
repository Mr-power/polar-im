package com.aias.polar.im.server.service.impl;

import com.aias.polar.im.server.dao.ImUserSessionDao;
import com.aias.polar.im.server.entity.ImUserSession;
import com.aias.polar.im.server.service.ImSessionsService;
import com.aias.polar.im.server.service.ImUserSessionService;
import com.aias.polar.im.server.service.StartSessionService;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * (ImUserSession)表服务实现类
 *
 * @author makejava
 * @since 2020-03-14 11:18:24
 */
@Service("imUserSessionService")
public class ImUserSessionServiceImpl implements ImUserSessionService {
    @Resource
    private ImUserSessionDao imUserSessionDao;
    @Resource
    private ImSessionsService sessionsService;
    @Resource
    private StartSessionService startSessionService;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ImUserSession queryById(Integer id) {
        return this.imUserSessionDao.queryById(id);
    }

    @Override
    public Integer queryToUserBySessionIdAndUserId(Integer sessionId, Integer fromUserId) {
        return imUserSessionDao.queryToUserIdBySessionIdAndFromUserId(sessionId, fromUserId);
    }

    @Override
    public Integer queryByUserIds(Integer userId, Integer toUserId) {
        // 根据当前用户 id 查询所有 session
        List<ImUserSession> sessionList = this.queryAllEvenClosedByUserId(userId);
        if (null == sessionList || sessionList.isEmpty()) {
            return null;
        }
        for (ImUserSession userSession : sessionList) {
            List<ImUserSession> userSessions = this.queryAllEvenClosedBySessionId(userSession.getSessionId());
            Optional<ImUserSession> optional =
                    userSessions.stream().filter(s -> s.getUserId().equals(toUserId)).findFirst();
            if (optional.isPresent()) {
                // 查到了如果这个session是关闭状态 打开 并返回
                ImUserSession targetSession = optional.get();
                ImUserSession mySession = this.queryEvenClosedBySessionIdAndUserId(targetSession.getSessionId(),
                        userId);
                if (mySession.getCloseFlag() == 1) {
                    mySession.setCloseFlag(0);
                    this.update(mySession);
                }
                // 如果不是关闭状态 直接返回

                return targetSession.getSessionId();
            }
        }
        // 之前不存在
        return null;
    }

    @Override
    public List<ImUserSession> queryAllEvenClosedByUserId(Integer userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        return imUserSessionDao.queryAllByMap(map);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ImUserSession> queryAllByLimit(int offset, int limit) {
        return this.imUserSessionDao.queryAllByLimit(offset, limit);
    }

    @Override
    public List<ImUserSession> queryAllByUserId(Integer userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", userId);
        map.put("closeFlag", 0);
        return imUserSessionDao.queryAllByMap(map);
    }

    @Override
    public List<ImUserSession> queryAllBySessionId(Integer sessionId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("sessionId", sessionId);
        map.put("closeFlag", 0);
        return imUserSessionDao.queryAllByMap(map);
    }

    @Override
    public List<ImUserSession> queryAllEvenClosedBySessionId(Integer sessionId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("sessionId", sessionId);
        return imUserSessionDao.queryAllByMap(map);
    }

    @Override
    public ImUserSession queryBySessionIdAndUserId(Integer sessionId, Integer userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("sessionId", sessionId);
        map.put("userId", userId);
        map.put("closeFlag", 0);
        List<ImUserSession> userSessionList = imUserSessionDao.queryAllByMap(map);
        if (null == userSessionList || userSessionList.isEmpty()) {
            return null;
        }
        return userSessionList.get(0);
    }

    @Override
    public ImUserSession queryEvenClosedBySessionIdAndUserId(Integer sessionId, Integer userId) {
        Map<String, Object> map = Maps.newHashMap();
        map.put("sessionId", sessionId);
        map.put("userId", userId);
        List<ImUserSession> userSessionList = imUserSessionDao.queryAllByMap(map);
        if (null == userSessionList || userSessionList.isEmpty()) {
            return null;
        }
        return userSessionList.get(0);
    }

    /**
     * 新增数据
     *
     * @param imUserSession 实例对象
     * @return 实例对象
     */
    @Override
    public ImUserSession insert(ImUserSession imUserSession) {
        this.imUserSessionDao.insert(imUserSession);
        return imUserSession;
    }

    /**
     * 修改数据
     *
     * @param imUserSession 实例对象
     * @return 实例对象
     */
    @Override
    public ImUserSession update(ImUserSession imUserSession) {
        this.imUserSessionDao.update(imUserSession);
        return this.queryById(imUserSession.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.imUserSessionDao.deleteById(id) > 0;
    }
}