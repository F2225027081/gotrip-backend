package com.ytzl.gotrip.rpc.service;

import com.ytzl.gotrip.model.GotripUser;

public interface LoginService {
    /**
     * 用户登录
     * @param gotripUser 用户信息
     * @param password 密码(用户输入)
     * @param userAgent
     * @throws Exception 登录失败原因
     */
    public String doLogin(GotripUser gotripUser, String password, String userAgent) throws Exception;

    /**
     * 注销
     * @param token 令牌
     * @param userAgent 浏览器内核版本信息
     * @throws Exception 失败原因
     */
    void logout(String token,String userAgent)throws Exception;
}
