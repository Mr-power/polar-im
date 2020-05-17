package com.aias.polar.im.server.service.impl;

import com.aias.polar.im.server.entity.ImUser;
import com.aias.polar.im.server.service.IAuthService;
import com.aias.polar.im.server.service.ImUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author liuhy
 * @since 2020/3/13
 */
@Service
public class IAuthServiceImpl implements IAuthService {

    @Resource
    private ImUserService imUserService;

    @Override
    public boolean checkLogin(String account, String password) {

        ImUser user = imUserService.queryByAccount(account);

        // 如果没有查到直接返回 false
        if (null == user) {
            return false;
        }

        // TODO 比较密码 如果密码不对 false

        return true;
    }
}
