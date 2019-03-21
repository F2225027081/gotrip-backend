package com.ytzl.gotrip.rpc.service;
import com.ytzl.gotrip.mapper.GotripHotelExtendPropertyMapper;
import com.ytzl.gotrip.model.GotripHotelExtendProperty;
import com.ytzl.gotrip.rpc.api.RpcGotripHotelExtendPropertyService;
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
@Service(interfaceClass = RpcGotripHotelExtendPropertyService.class)
public class RpcGotripHotelExtendPropertyServiceImpl implements RpcGotripHotelExtendPropertyService {

    @Resource
    private GotripHotelExtendPropertyMapper gotripHotelExtendPropertyMapper;

    @Override
    public GotripHotelExtendProperty getGotripHotelExtendPropertyById(Long id)throws Exception{
        return gotripHotelExtendPropertyMapper.getGotripHotelExtendPropertyById(id);
    }

    @Override
    public List<GotripHotelExtendProperty>	getGotripHotelExtendPropertyListByMap(Map<String,Object> param)throws Exception{
        return gotripHotelExtendPropertyMapper.getGotripHotelExtendPropertyListByMap(param);
    }

    @Override
    public Integer getGotripHotelExtendPropertyCountByMap(Map<String,Object> param)throws Exception{
        return gotripHotelExtendPropertyMapper.getGotripHotelExtendPropertyCountByMap(param);
    }

    @Override
    public Integer insertGotripHotelExtendProperty(GotripHotelExtendProperty gotripHotelExtendProperty)throws Exception{
            gotripHotelExtendProperty.setCreationDate(new Date());
            return gotripHotelExtendPropertyMapper.insertGotripHotelExtendProperty(gotripHotelExtendProperty);
    }

    @Override
    public Integer updateGotripHotelExtendProperty(GotripHotelExtendProperty gotripHotelExtendProperty)throws Exception{
        gotripHotelExtendProperty.setModifyDate(new Date());
        return gotripHotelExtendPropertyMapper.updateGotripHotelExtendProperty(gotripHotelExtendProperty);
    }

    @Override
    public Integer deleteGotripHotelExtendPropertyById(Long id)throws Exception{
        return gotripHotelExtendPropertyMapper.deleteGotripHotelExtendPropertyById(id);
    }

    @Override
    public Page<GotripHotelExtendProperty> queryGotripHotelExtendPropertyPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = gotripHotelExtendPropertyMapper.getGotripHotelExtendPropertyCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<GotripHotelExtendProperty> gotripHotelExtendPropertyList = gotripHotelExtendPropertyMapper.getGotripHotelExtendPropertyListByMap(param);
        page.setRows(gotripHotelExtendPropertyList);
        return page;
    }

}
