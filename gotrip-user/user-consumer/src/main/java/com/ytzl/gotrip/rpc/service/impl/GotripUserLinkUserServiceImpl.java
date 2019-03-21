package com.ytzl.gotrip.rpc.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.ytzl.gotrip.ext.utils.RedisUtils;
import com.ytzl.gotrip.model.GotripUserLinkUser;
import com.ytzl.gotrip.rpc.api.RpcGotripUserLinkUserService;
import com.ytzl.gotrip.rpc.service.GotripUserLinkUserService;
import com.ytzl.gotrip.utils.common.DigestUtil;
import com.ytzl.gotrip.utils.common.ErrorCode;
import com.ytzl.gotrip.utils.exception.GotripException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GotripUserLinkUserServiceImpl implements GotripUserLinkUserService {

    @Reference
    private RpcGotripUserLinkUserService rpcGotripUserLinkUserService;



    @Resource
    private RedisUtils redisUtils;

    /**
     * 根据当前登录用户Id查询相关联系人And根据相关联系人名称模糊查询
     * @return
     */
    @Override
    public List<GotripUserLinkUser> queryuserlinkuser(String token,String agent,GotripUserLinkUser gotripUserLinkUser) throws Exception {

        //保存agent前6位，并转为MD5格式
        String md5Agent = DigestUtil.hmacSign(agent,6);
        //判断是否与当前浏览器token一致
        if(!token.contains(md5Agent)){
            throw new GotripException("token已失效！", ErrorCode.AUTH_TOKEN_INVALID);
        }



        return rpcGotripUserLinkUserService.getGotripUserLinkUserListByMap(gotripUserLinkUser);
    }


    /**
     * 添加常用联系人
     * @param gotripUserLinkUser 常用联系人信息
     * @return 查询返回的条数
     */
    @Override
    public Integer insertGotripUserLinkUser(String token, String agent, GotripUserLinkUser gotripUserLinkUser) throws Exception {
        //保存agent前6位，并转为MD5格式
        String md5Agent = DigestUtil.hmacSign(agent,6);
        //判断是否与当前浏览器token一致
        if(!token.contains(md5Agent)){
            throw new GotripException("token已失效！", ErrorCode.AUTH_TOKEN_INVALID);
        }


        /*System.out.println(gotripUserLinkUser.getLinkUserName());
        Integer num = rpcGotripUserLinkUserService.findUserLinkUserByName(gotripUserLinkUser.getLinkUserName());

        //判断是否有当前添加的联系人
        if(num != 0){
            throw new GotripException("已有该联系人！",ErrorCode.AUTH_TOKEN_INVALID);
        }*/

        return rpcGotripUserLinkUserService.insertGotripUserLinkUser(gotripUserLinkUser);
    }


    /**
     * 修改
     * @param token
     * @param agent
     * @param gotripUserLinkUser
     * @return
     */
    @Override
    public Integer updateGotripUserLinkUser(String token, String agent, GotripUserLinkUser gotripUserLinkUser) throws Exception {
        //保存agent前6位，并转为MD5格式
        String md5Agent = DigestUtil.hmacSign(agent,6);
        //判断是否与当前浏览器token一致
        if(!token.contains(md5Agent)){
            throw new GotripException("token已失效！", ErrorCode.AUTH_TOKEN_INVALID);
        }

        return rpcGotripUserLinkUserService.updateGotripUserLinkUser(gotripUserLinkUser);
    }

    /**
     * 多选删除
     * @param id
     * @return
     */
    @Override
    public Integer deleteGotripUserLinkUserById(long id) throws Exception {

        return rpcGotripUserLinkUserService.deleteGotripUserLinkUserById(id);
    }
}
