package com.ytzl.gotrip.rpc.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ytzl.gotrip.model.GotripUser;
import com.ytzl.gotrip.rpc.api.RpcTokenService;
import com.ytzl.gotrip.rpc.service.LoginService;
import com.ytzl.gotrip.utils.common.Constants;
import com.ytzl.gotrip.utils.common.DigestUtil;
import com.ytzl.gotrip.utils.common.EmptyUtils;
import com.ytzl.gotrip.utils.common.ErrorCode;
import com.ytzl.gotrip.utils.exception.GotripException;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Reference
    RpcTokenService rpcTokenService;

    @Override
    public String doLogin(GotripUser gotripUser, String password, String userAgent) throws Exception {

        //判断用户是否为空
        if (EmptyUtils.isEmpty(gotripUser)) {
            throw new GotripException("用户不存在!", ErrorCode.AUTH_PARAMETER_ERROR);
        }

        //判断密码
        String md5Password = DigestUtil.hmacSign(password);//把用户输入的密码转换为md5格式
        if (!md5Password.equals(gotripUser.getUserPassword())) {//进行判断
            throw new GotripException("用户名或密码错误",ErrorCode.AUTH_PARAMETER_ERROR);
        }

        //用户是否激活
        if (gotripUser.getActivated() != Constants.UserActivateStatus.USER_ACTIVATE_ENABLE) {
            throw new GotripException("用户未激活!",ErrorCode.AUTH_PARAMETER_ERROR);
        }

        //用户密码制空
        gotripUser.setUserPassword(null);

        //生成token
        String token = rpcTokenService.gengerateToken(gotripUser, userAgent);
        //保存token
        rpcTokenService.saveToken(token,gotripUser);
        return token;
    }

    /**
     * 退出登录
     * @param token 令牌
     * @param userAgent 浏览器内核版本信息
     * @throws Exception
     */
    @Override
    public void logout(String token, String userAgent) throws Exception {
        if (rpcTokenService.verifToken(token,userAgent)) {
            rpcTokenService.removeToken(token);
        }else{
            throw new GotripException("Token无效",ErrorCode.AUTH_TOKEN_INVALID);
        }
    }
}
