package com.ytzl.gotrip.rpc.service;

import com.ytzl.gotrip.model.GotripUser;
import com.ytzl.gotrip.vo.userinfo.ItripUserVO;

public interface GotripUserService {

    /**
     * 根据登录账号查询用户信息
     * @param userCode 登录账号
     * @return 用户信息(包含密码)
     */
    public GotripUser findByUserCode(String userCode) throws Exception;


    void registerByPhone(ItripUserVO itripUserVO) throws Exception;

    /**
     * 手机账号激活
     *
     * @param user 登录账号
     * @param code 验证码
     */
    void validatePhone(String user, String code) throws Exception;



    void registerByMail(ItripUserVO itripUserVO) throws Exception;

    //邮箱激活
    void validatemail(String mail,String code) throws Exception;

}
