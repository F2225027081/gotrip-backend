package com.ytzl.gotrip.rpc.service;

import com.alibaba.fastjson.JSON;
import com.ytzl.gotrip.mapper.GotripUserLinkUserMapper;
import com.ytzl.gotrip.model.GotripUserLinkUser;
import com.ytzl.gotrip.rpc.api.RpcGotripUserLinkUserService;
import com.ytzl.gotrip.utils.common.EmptyUtils;
import com.ytzl.gotrip.utils.common.Page;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ytzl.gotrip.utils.common.Constants;

@Component
@Service(interfaceClass = RpcGotripUserLinkUserService.class)
public class RpcGotripUserLinkUserServiceImpl implements RpcGotripUserLinkUserService {

    @Resource
    private GotripUserLinkUserMapper gotripUserLinkUserMapper;






    /**
     * 根据当前登录用户Id查询相关联系人
     * @return
     */
    @Override
    public List<GotripUserLinkUser> getGotripUserLinkUserListByMap(GotripUserLinkUser gotripUserLinkUser) throws Exception {
        String toJsonString = JSON.toJSONString(gotripUserLinkUser);
        Map params = JSON.parseObject(toJsonString, Map.class);
        List<GotripUserLinkUser> list = gotripUserLinkUserMapper.getGotripUserLinkUserListByMap(params);

        return list;
    }


    @Override
    public GotripUserLinkUser getGotripUserLinkUserById(Long id) throws Exception {
        return gotripUserLinkUserMapper.getGotripUserLinkUserById(id);
    }


    @Override
    public Integer getGotripUserLinkUserCountByMap(Map<String, Object> param) throws Exception {
        return gotripUserLinkUserMapper.getGotripUserLinkUserCountByMap(param);
    }


    /**
     * 添加
     * @param gotripUserLinkUser
     * @return
     * @throws Exception
     */
    @Override
    public Integer insertGotripUserLinkUser(GotripUserLinkUser gotripUserLinkUser) throws Exception {
        gotripUserLinkUser.setCreationDate(new Date());
        return gotripUserLinkUserMapper.insertGotripUserLinkUser(gotripUserLinkUser);
    }

    /**
     * 修改
     * @param gotripUserLinkUser
     * @return
     * @throws Exception
     */
    @Override
    public Integer updateGotripUserLinkUser(GotripUserLinkUser gotripUserLinkUser) throws Exception {

        return gotripUserLinkUserMapper.updateGotripUserLinkUser(gotripUserLinkUser);
    }


    /**
     * 多选删除
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteGotripUserLinkUserById(long id) throws Exception {

        return gotripUserLinkUserMapper.deleteGotripUserLinkUserById(id);
    }

    @Override
    public Page<GotripUserLinkUser> queryGotripUserLinkUserPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize) throws Exception {
        Integer total = gotripUserLinkUserMapper.getGotripUserLinkUserCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<GotripUserLinkUser> gotripUserLinkUserList = gotripUserLinkUserMapper.getGotripUserLinkUserListByMap(param);
        page.setRows(gotripUserLinkUserList);
        return page;
    }


    /**
     * 根据常用联系人名称查找用户
     * @param linkUserName
     * @return
     */
    @Override
    public Integer findUserLinkUserByName(String linkUserName) throws Exception {

        return gotripUserLinkUserMapper.findUserLinkUserByName(linkUserName);
    }

}
