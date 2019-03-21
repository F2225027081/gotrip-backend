package com.ytzl.gotrip.rpc.api;

import com.ytzl.gotrip.model.GotripUserLinkUser;
import com.ytzl.gotrip.utils.common.Page;
import org.jboss.logging.Param;

import java.util.List;
import java.util.Map;


/**
* Rpc服务接口
*
* @author jayden
*/
public interface RpcGotripUserLinkUserService {

    public GotripUserLinkUser getGotripUserLinkUserById(Long id)throws Exception;

    public List<GotripUserLinkUser>	getGotripUserLinkUserListByMap(GotripUserLinkUser gotripUserLinkUser)throws Exception;

    public Integer getGotripUserLinkUserCountByMap(Map<String, Object> param)throws Exception;

    public Integer insertGotripUserLinkUser(GotripUserLinkUser gotripUserLinkUser)throws Exception;

    public Integer updateGotripUserLinkUser(GotripUserLinkUser gotripUserLinkUser)throws Exception;

    public Integer deleteGotripUserLinkUserById(long id)throws Exception;

    public Page<GotripUserLinkUser> queryGotripUserLinkUserPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;





    /**
     * 根据常用联系人名称查找用户
     * @param linkUserName
     * @return
     */
    public Integer findUserLinkUserByName(String linkUserName) throws Exception;

}
