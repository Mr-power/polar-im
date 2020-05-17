package com.aias.polar.im.server.service.impl;

import com.aias.polar.im.server.dao.ImUserDao;
import com.aias.polar.im.server.entity.ImUser;
import com.aias.polar.im.server.service.ImUserService;
import com.aias.polar.jwt.utils.JwtUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ImUser)表服务实现类
 *
 * @author makejava
 * @since 2020-03-12 23:33:33
 */
@Service("imUserService")
public class ImUserServiceImpl implements ImUserService {
    @Resource
    private ImUserDao imUserDao;

    @Resource
    private JwtUtils jwtUtils;

    @Override
    public ImUser queryByToken(String token) {
        String account = jwtUtils.getSubjectFromJwt(token);
        return this
                .queryByAccount(account);
    }

    @Override
    public ImUser queryByName(String name) {
        return imUserDao.queryByName(name);
    }

    @Override
    public ImUser queryByAccount(String account) {
        return imUserDao.queryByAccount(account);
    }

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ImUser queryById(Integer id) {
        return this.imUserDao.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<ImUser> queryAllByLimit(int offset, int limit) {
        return this.imUserDao.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param imUser 实例对象
     * @return 实例对象
     */
    @Override
    public ImUser insert(ImUser imUser) {
        this.imUserDao.insert(imUser);
        return imUser;
    }

    /**
     * 修改数据
     *
     * @param imUser 实例对象
     * @return 实例对象
     */
    @Override
    public ImUser update(ImUser imUser) {
        this.imUserDao.update(imUser);
        return this.queryById(imUser.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Object id) {
        return this.imUserDao.deleteById(id) > 0;
    }
}