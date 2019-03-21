package com.ytzl.gotrip.rpc.service;
import com.ytzl.gotrip.mapper.GotripHotelTradingAreaMapper;
import com.ytzl.gotrip.model.GotripHotelTradingArea;
import com.ytzl.gotrip.rpc.api.RpcGotripHotelTradingAreaService;
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
@Service(interfaceClass = RpcGotripHotelTradingAreaService.class)
public class RpcGotripHotelTradingAreaServiceImpl implements RpcGotripHotelTradingAreaService {

    @Resource
    private GotripHotelTradingAreaMapper gotripHotelTradingAreaMapper;

    @Override
    public GotripHotelTradingArea getGotripHotelTradingAreaById(Long id)throws Exception{
        return gotripHotelTradingAreaMapper.getGotripHotelTradingAreaById(id);
    }

    @Override
    public List<GotripHotelTradingArea>	getGotripHotelTradingAreaListByMap(Map<String,Object> param)throws Exception{
        return gotripHotelTradingAreaMapper.getGotripHotelTradingAreaListByMap(param);
    }

    @Override
    public Integer getGotripHotelTradingAreaCountByMap(Map<String,Object> param)throws Exception{
        return gotripHotelTradingAreaMapper.getGotripHotelTradingAreaCountByMap(param);
    }

    @Override
    public Integer insertGotripHotelTradingArea(GotripHotelTradingArea gotripHotelTradingArea)throws Exception{
            gotripHotelTradingArea.setCreationDate(new Date());
            return gotripHotelTradingAreaMapper.insertGotripHotelTradingArea(gotripHotelTradingArea);
    }

    @Override
    public Integer updateGotripHotelTradingArea(GotripHotelTradingArea gotripHotelTradingArea)throws Exception{
        gotripHotelTradingArea.setModifyDate(new Date());
        return gotripHotelTradingAreaMapper.updateGotripHotelTradingArea(gotripHotelTradingArea);
    }

    @Override
    public Integer deleteGotripHotelTradingAreaById(Long id)throws Exception{
        return gotripHotelTradingAreaMapper.deleteGotripHotelTradingAreaById(id);
    }

    @Override
    public Page<GotripHotelTradingArea> queryGotripHotelTradingAreaPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = gotripHotelTradingAreaMapper.getGotripHotelTradingAreaCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<GotripHotelTradingArea> gotripHotelTradingAreaList = gotripHotelTradingAreaMapper.getGotripHotelTradingAreaListByMap(param);
        page.setRows(gotripHotelTradingAreaList);
        return page;
    }

}
