package com.ytzl.gotrip.rpc;
import com.ytzl.gotrip.mapper.GotripOrderMapper;
import com.ytzl.gotrip.model.GotripOrder;
import com.ytzl.gotrip.rpc.api.RpcGotripOrderService;
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
@Service(interfaceClass = RpcGotripOrderService.class)
public class RpcGotripOrderServiceImpl implements RpcGotripOrderService {

    @Resource
    private GotripOrderMapper gotripOrderMapper;

    @Override
    public GotripOrder getGotripOrderById(Long id)throws Exception{
        return gotripOrderMapper.getGotripOrderById(id);
    }

    @Override
    public List<GotripOrder>	getGotripOrderListByMap(Map<String,Object> param)throws Exception{
        return gotripOrderMapper.getGotripOrderListByMap(param);
    }

    @Override
    public Integer getGotripOrderCountByMap(Map<String,Object> param)throws Exception{
        return gotripOrderMapper.getGotripOrderCountByMap(param);
    }

    @Override
    public Integer insertGotripOrder(GotripOrder gotripOrder)throws Exception{
            gotripOrder.setCreationDate(new Date());
            return gotripOrderMapper.insertGotripOrder(gotripOrder);
    }

    @Override
    public Integer updateGotripOrder(GotripOrder gotripOrder)throws Exception{
        gotripOrder.setModifyDate(new Date());
        return gotripOrderMapper.updateGotripOrder(gotripOrder);
    }

    @Override
    public Integer deleteGotripOrderById(Long id)throws Exception{
        return gotripOrderMapper.deleteGotripOrderById(id);
    }

    @Override
    public Page<GotripOrder> queryGotripOrderPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = gotripOrderMapper.getGotripOrderCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<GotripOrder> gotripOrderList = gotripOrderMapper.getGotripOrderListByMap(param);
        page.setRows(gotripOrderList);
        return page;
    }

}
