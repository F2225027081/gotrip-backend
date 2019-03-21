package com.ytzl.gotrip.rpc.service;
import com.ytzl.gotrip.mapper.GotripHotelFeatureMapper;
import com.ytzl.gotrip.model.GotripHotelFeature;
import com.ytzl.gotrip.rpc.api.RpcGotripHotelFeatureService;
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
@Service(interfaceClass = RpcGotripHotelFeatureService.class)
public class RpcGotripHotelFeatureServiceImpl implements RpcGotripHotelFeatureService {

    @Resource
    private GotripHotelFeatureMapper gotripHotelFeatureMapper;

    @Override
    public GotripHotelFeature getGotripHotelFeatureById(Long id)throws Exception{
        return gotripHotelFeatureMapper.getGotripHotelFeatureById(id);
    }

    @Override
    public List<GotripHotelFeature>	getGotripHotelFeatureListByMap(Map<String,Object> param)throws Exception{
        return gotripHotelFeatureMapper.getGotripHotelFeatureListByMap(param);
    }

    @Override
    public Integer getGotripHotelFeatureCountByMap(Map<String,Object> param)throws Exception{
        return gotripHotelFeatureMapper.getGotripHotelFeatureCountByMap(param);
    }

    @Override
    public Integer insertGotripHotelFeature(GotripHotelFeature gotripHotelFeature)throws Exception{
            gotripHotelFeature.setCreationDate(new Date());
            return gotripHotelFeatureMapper.insertGotripHotelFeature(gotripHotelFeature);
    }

    @Override
    public Integer updateGotripHotelFeature(GotripHotelFeature gotripHotelFeature)throws Exception{
        gotripHotelFeature.setModifyDate(new Date());
        return gotripHotelFeatureMapper.updateGotripHotelFeature(gotripHotelFeature);
    }

    @Override
    public Integer deleteGotripHotelFeatureById(Long id)throws Exception{
        return gotripHotelFeatureMapper.deleteGotripHotelFeatureById(id);
    }

    @Override
    public Page<GotripHotelFeature> queryGotripHotelFeaturePageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = gotripHotelFeatureMapper.getGotripHotelFeatureCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<GotripHotelFeature> gotripHotelFeatureList = gotripHotelFeatureMapper.getGotripHotelFeatureListByMap(param);
        page.setRows(gotripHotelFeatureList);
        return page;
    }

}
