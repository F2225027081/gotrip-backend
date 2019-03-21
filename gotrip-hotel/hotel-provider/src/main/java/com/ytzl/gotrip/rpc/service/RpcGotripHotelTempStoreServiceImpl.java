package com.ytzl.gotrip.rpc.service;
import com.ytzl.gotrip.mapper.GotripHotelTempStoreMapper;
import com.ytzl.gotrip.model.GotripHotelTempStore;
import com.ytzl.gotrip.rpc.api.RpcGotripHotelTempStoreService;
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
@Service(interfaceClass = RpcGotripHotelTempStoreService.class)
public class RpcGotripHotelTempStoreServiceImpl implements RpcGotripHotelTempStoreService {

    @Resource
    private GotripHotelTempStoreMapper gotripHotelTempStoreMapper;

    @Override
    public GotripHotelTempStore getGotripHotelTempStoreById(Long id)throws Exception{
        return gotripHotelTempStoreMapper.getGotripHotelTempStoreById(id);
    }

    @Override
    public List<GotripHotelTempStore>	getGotripHotelTempStoreListByMap(Map<String,Object> param)throws Exception{
        return gotripHotelTempStoreMapper.getGotripHotelTempStoreListByMap(param);
    }

    @Override
    public Integer getGotripHotelTempStoreCountByMap(Map<String,Object> param)throws Exception{
        return gotripHotelTempStoreMapper.getGotripHotelTempStoreCountByMap(param);
    }

    @Override
    public Integer insertGotripHotelTempStore(GotripHotelTempStore gotripHotelTempStore)throws Exception{
            gotripHotelTempStore.setCreationDate(new Date());
            return gotripHotelTempStoreMapper.insertGotripHotelTempStore(gotripHotelTempStore);
    }

    @Override
    public Integer updateGotripHotelTempStore(GotripHotelTempStore gotripHotelTempStore)throws Exception{
        gotripHotelTempStore.setModifyDate(new Date());
        return gotripHotelTempStoreMapper.updateGotripHotelTempStore(gotripHotelTempStore);
    }

    @Override
    public Integer deleteGotripHotelTempStoreById(Long id)throws Exception{
        return gotripHotelTempStoreMapper.deleteGotripHotelTempStoreById(id);
    }

    @Override
    public Page<GotripHotelTempStore> queryGotripHotelTempStorePageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = gotripHotelTempStoreMapper.getGotripHotelTempStoreCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<GotripHotelTempStore> gotripHotelTempStoreList = gotripHotelTempStoreMapper.getGotripHotelTempStoreListByMap(param);
        page.setRows(gotripHotelTempStoreList);
        return page;
    }

}
