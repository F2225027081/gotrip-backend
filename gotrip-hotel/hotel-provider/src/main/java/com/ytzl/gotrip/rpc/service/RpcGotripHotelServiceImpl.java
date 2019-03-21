package com.ytzl.gotrip.rpc.service;
import com.ytzl.gotrip.mapper.GotripHotelMapper;
import com.ytzl.gotrip.model.GotripHotel;
import com.ytzl.gotrip.rpc.api.RpcGotripHotelService;
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
@Service(interfaceClass = RpcGotripHotelService.class)
public class RpcGotripHotelServiceImpl implements RpcGotripHotelService {

    @Resource
    private GotripHotelMapper gotripHotelMapper;

    @Override
    public GotripHotel getGotripHotelById(Long id)throws Exception{
        return gotripHotelMapper.getGotripHotelById(id);
    }

    @Override
    public List<GotripHotel>	getGotripHotelListByMap(Map<String,Object> param)throws Exception{
        return gotripHotelMapper.getGotripHotelListByMap(param);
    }

    @Override
    public Integer getGotripHotelCountByMap(Map<String,Object> param)throws Exception{
        return gotripHotelMapper.getGotripHotelCountByMap(param);
    }

    @Override
    public Integer insertGotripHotel(GotripHotel gotripHotel)throws Exception{
            gotripHotel.setCreationDate(new Date());
            return gotripHotelMapper.insertGotripHotel(gotripHotel);
    }

    @Override
    public Integer updateGotripHotel(GotripHotel gotripHotel)throws Exception{
        gotripHotel.setModifyDate(new Date());
        return gotripHotelMapper.updateGotripHotel(gotripHotel);
    }

    @Override
    public Integer deleteGotripHotelById(Long id)throws Exception{
        return gotripHotelMapper.deleteGotripHotelById(id);
    }

    @Override
    public Page<GotripHotel> queryGotripHotelPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = gotripHotelMapper.getGotripHotelCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<GotripHotel> gotripHotelList = gotripHotelMapper.getGotripHotelListByMap(param);
        page.setRows(gotripHotelList);
        return page;
    }

}
