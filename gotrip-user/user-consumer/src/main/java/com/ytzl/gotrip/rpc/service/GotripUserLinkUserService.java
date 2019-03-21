package com.ytzl.gotrip.rpc.service;

import com.ytzl.gotrip.model.GotripUserLinkUser;

import java.util.List;

public interface GotripUserLinkUserService {
    /**
     * 根据当前登录用户Id查询相关联系人
     * @return
     */
    public List<GotripUserLinkUser> queryuserlinkuser(String token,String agent, GotripUserLinkUser gotripUserLinkUser) throws Exception;

    public Integer insertGotripUserLinkUser(String token,String agent,GotripUserLinkUser gotripUserLinkUser) throws Exception;


    public Integer updateGotripUserLinkUser(String token, String agent, GotripUserLinkUser gotripUserLinkUser) throws Exception;

    public Integer deleteGotripUserLinkUserById(long id) throws Exception;
}
