package com.aias.polar.im.server.service;

/**
 * @author liuhy
 * @since 2020/3/13
 */
public interface AuthService {

    boolean checkLogin(String account, String password);

}
