package com.ytzl.gotrip.rpc;
import com.ytzl.gotrip.mapper.GotripOrderLinkUserMapper;
import com.ytzl.gotrip.model.GotripOrderLinkUser;
import com.ytzl.gotrip.rpc.api.RpcGotripOrderLinkUserService;
import com.ytzl.gotrip.utils.common.EmptyUtils;
import com.ytzl.gotrip.utils.common.Page;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.ytzl.gotrip.utils.common.Constants;

@Component
@Service(interfaceClass = RpcGotripOrderLinkUserService.class)
public class RpcGotripOrderLinkUserServiceImpl implements RpcGotripOrderLinkUserService {

    @Resource
    private GotripOrderLinkUserMapper gotripOrderLinkUserMapper;

    @Override
    public GotripOrderLinkUser getGotripOrderLinkUserById(Long id)throws Exception{
        return gotripOrderLinkUserMapper.getGotripOrderLinkUserById(id);
    }

    @Override
    public List<GotripOrderLinkUser>	getGotripOrderLinkUserListByMap(Map<String,Object> param)throws Exception{
        return gotripOrderLinkUserMapper.getGotripOrderLinkUserListByMap(param);
    }

    @Override
    public Integer getGotripOrderLinkUserCountByMap(Map<String,Object> param)throws Exception{
        return gotripOrderLinkUserMapper.getGotripOrderLinkUserCountByMap(param);
    }

    @Override
    public Integer insertGotripOrderLinkUser(GotripOrderLinkUser gotripOrderLinkUser)throws Exception{
            gotripOrderLinkUser.setCreationDate(new Date());
            return gotripOrderLinkUserMapper.insertGotripOrderLinkUser(gotripOrderLinkUser);
    }

    @Override
    public Integer updateGotripOrderLinkUser(GotripOrderLinkUser gotripOrderLinkUser)throws Exception{
        gotripOrderLinkUser.setModifyDate(new Date());
        return gotripOrderLinkUserMapper.updateGotripOrderLinkUser(gotripOrderLinkUser);
    }

    @Override
    public Integer deleteGotripOrderLinkUserById(Long id)throws Exception{
        return gotripOrderLinkUserMapper.deleteGotripOrderLinkUserById(id);
    }

    @Override
    public Page<GotripOrderLinkUser> queryGotripOrderLinkUserPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = gotripOrderLinkUserMapper.getGotripOrderLinkUserCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<GotripOrderLinkUser> gotripOrderLinkUserList = gotripOrderLinkUserMapper.getGotripOrderLinkUserListByMap(param);
        page.setRows(gotripOrderLinkUserList);
        return page;
    }

}
