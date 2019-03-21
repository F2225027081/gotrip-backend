package com.ytzl.gotrip.rpc;
import com.ytzl.gotrip.mapper.GotripTradeEndsMapper;
import com.ytzl.gotrip.model.GotripTradeEnds;
import com.ytzl.gotrip.rpc.api.RpcGotripTradeEndsService;
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
@Service(interfaceClass = RpcGotripTradeEndsService.class)
public class RpcGotripTradeEndsServiceImpl implements RpcGotripTradeEndsService {

    @Resource
    private GotripTradeEndsMapper gotripTradeEndsMapper;

    @Override
    public GotripTradeEnds getGotripTradeEndsById(Long id)throws Exception{
        return gotripTradeEndsMapper.getGotripTradeEndsById(id);
    }

    @Override
    public List<GotripTradeEnds>	getGotripTradeEndsListByMap(Map<String,Object> param)throws Exception{
        return gotripTradeEndsMapper.getGotripTradeEndsListByMap(param);
    }

    @Override
    public Integer getGotripTradeEndsCountByMap(Map<String,Object> param)throws Exception{
        return gotripTradeEndsMapper.getGotripTradeEndsCountByMap(param);
    }

    @Override
    public Integer insertGotripTradeEnds(GotripTradeEnds gotripTradeEnds)throws Exception{
            return gotripTradeEndsMapper.insertGotripTradeEnds(gotripTradeEnds);
    }

    @Override
    public Integer updateGotripTradeEnds(GotripTradeEnds gotripTradeEnds)throws Exception{
        return gotripTradeEndsMapper.updateGotripTradeEnds(gotripTradeEnds);
    }

    @Override
    public Integer deleteGotripTradeEndsById(Long id)throws Exception{
        return gotripTradeEndsMapper.deleteGotripTradeEndsById(id);
    }

    @Override
    public Page<GotripTradeEnds> queryGotripTradeEndsPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = gotripTradeEndsMapper.getGotripTradeEndsCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<GotripTradeEnds> gotripTradeEndsList = gotripTradeEndsMapper.getGotripTradeEndsListByMap(param);
        page.setRows(gotripTradeEndsList);
        return page;
    }

}
