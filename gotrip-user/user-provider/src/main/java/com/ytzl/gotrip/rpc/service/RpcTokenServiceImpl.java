package com.ytzl.gotrip.rpc.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.ytzl.gotrip.ext.utils.RedisUtils;
import com.ytzl.gotrip.model.GotripUser;
import com.ytzl.gotrip.rpc.api.RpcTokenService;
import com.ytzl.gotrip.utils.common.Constants;
import com.ytzl.gotrip.utils.common.DigestUtil;
import com.ytzl.gotrip.utils.common.ErrorCode;
import com.ytzl.gotrip.utils.common.UserAgentUtil;
import com.ytzl.gotrip.utils.exception.GotripException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Token服务提供
 */
@Component
@Service(interfaceClass = RpcTokenService.class)
public class RpcTokenServiceImpl implements RpcTokenService {
    @Resource
    private RedisUtils redisUtils;

    /**
     * 构建Token
     *
     * @param gotripUser 用户信息
     * @param userAgent  浏览器内核版本
     * @return
     */
    @Override
    public String gengerateToken(GotripUser gotripUser, String userAgent) {
        //token:[MOBILE|PC]-userCode(md5)-userId-yyyyMMddHhmmss-浏览器标识
        StringBuffer sbToken = new StringBuffer("token:");
        if (UserAgentUtil.checkAgent(userAgent)) {
            //移动
            sbToken.append("MOBILE-");
        } else {
            sbToken.append("PC-");
        }

        String md5UserCode = DigestUtil.hmacSign(gotripUser.getUserCode());
        sbToken.append(md5UserCode).append("-");
        sbToken.append(gotripUser.getId()).append("-");
        //yyyyMMddHhmmss
        String createTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        sbToken.append(createTime).append("-");

        String md5UserAgent = DigestUtil.hmacSign(userAgent, 6);
        sbToken.append(md5UserAgent);
        return sbToken.toString();
    }


    /**
     * 保存token
     *
     * @param token      token令牌
     * @param gotripUser 用户信息
     */
    @Override
    public void saveToken(String token, GotripUser gotripUser) {
        if (token.contains("PC-")) {
            redisUtils.set(token, JSON.toJSONString(gotripUser), Constants.RedisExpire.SESSION_TIMEOUT);
        } else if (token.contains("MOBILE-")) {
            redisUtils.set(token, JSON.toJSONString(gotripUser));
        }

    }

    /**
     * 验证token是否存在
     *
     * @param token     token令牌
     * @param userAgent 浏览器内核版本
     * @return
     */
    @Override
    public boolean verifToken(String token, String userAgent) {
        //token是否存在
        if (!redisUtils.exist(token)) {
            return false;
        }

        //创建token浏览器和当前浏览器是否一致
        String md5UserAgent = DigestUtil.hmacSign(userAgent, 6);

        return token.contains(md5UserAgent);
    }


    /**
     * 删除token
     *
     * @param token token令牌
     */
    @Override
    public void removeToken(String token) {
        redisUtils.expire(token, 3);
    }


    /**
     * 获取用户信息
     *
     * @param token     token令牌
     * @param userAgent 浏览器标识
     * @return
     */
    @Override
    public GotripUser getGotripUser(String token, String userAgent) throws GotripException {
        //验证浏览器
        if (!this.verifToken(token, userAgent)) {
            throw new GotripException("Token无效", ErrorCode.AUTH_TOKEN_INVALID);
        }
        //获取用户信息
        String gotripUserJson = (String) redisUtils.get(token);
        return JSON.parseObject(gotripUserJson, GotripUser.class);
    }




}
