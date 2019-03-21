package com.ytzl.gotrip.rpc.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ytzl.gotrip.ext.utils.RedisUtils;
import com.ytzl.gotrip.model.GotripUser;
import com.ytzl.gotrip.rpc.api.RpcTokenService;
import com.ytzl.gotrip.rpc.service.TokenService;
import com.ytzl.gotrip.utils.common.Constants;
import com.ytzl.gotrip.utils.common.ErrorCode;
import com.ytzl.gotrip.utils.exception.GotripException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;

@Service
public class TokenServiceImpl implements TokenService {

    @Reference
    private RpcTokenService rpcTokenService;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public void retoken(String token, String userAgent) throws GotripException, ParseException {

            //1.验证Token是否有效
            if(rpcTokenService.verifToken(token,userAgent)){
                //2.token置换保护器和ttl获取的剩余时间必须大于0
                long TokenRemTime = redisUtils.ttl(token)*1000;//Token剩余的时间(毫秒)
                //3.置换保护期(1h) 且 剩余时间 > 0
                if(TokenRemTime < Constants.RedisExpire.SESSION_TIMEOUT*500 && TokenRemTime>0){
                    //4.获取用户信息
                    GotripUser gotripUser = rpcTokenService.getGotripUser(token, userAgent);
                    //保存新的token
                    String tokenNew = rpcTokenService.gengerateToken(gotripUser, userAgent);
                    //保存token
                    rpcTokenService.saveToken(tokenNew,gotripUser);
                    //5.旧token删除
                    rpcTokenService.removeToken(token);

                }else{
                    throw new GotripException("token处于置换保护期间或token无效，禁止置换", ErrorCode.AUTH_TOKEN_INVALID);

                }

            }else{
                throw new GotripException("Token无效!",ErrorCode.AUTH_TOKEN_INVALID);
            }


    }
}
