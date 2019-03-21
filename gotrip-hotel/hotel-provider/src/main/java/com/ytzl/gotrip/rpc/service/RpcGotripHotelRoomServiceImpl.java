package com.ytzl.gotrip.rpc.service;
import com.ytzl.gotrip.mapper.GotripHotelRoomMapper;
import com.ytzl.gotrip.model.GotripHotelRoom;
import com.ytzl.gotrip.rpc.api.RpcGotripHotelRoomService;
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
@Service(interfaceClass = RpcGotripHotelRoomService.class)
public class RpcGotripHotelRoomServiceImpl implements RpcGotripHotelRoomService {

    @Resource
    private GotripHotelRoomMapper gotripHotelRoomMapper;

    @Override
    public GotripHotelRoom getGotripHotelRoomById(Long id)throws Exception{
        return gotripHotelRoomMapper.getGotripHotelRoomById(id);
    }

    @Override
    public List<GotripHotelRoom>	getGotripHotelRoomListByMap(Map<String,Object> param)throws Exception{
        return gotripHotelRoomMapper.getGotripHotelRoomListByMap(param);
    }

    @Override
    public Integer getGotripHotelRoomCountByMap(Map<String,Object> param)throws Exception{
        return gotripHotelRoomMapper.getGotripHotelRoomCountByMap(param);
    }

    @Override
    public Integer insertGotripHotelRoom(GotripHotelRoom gotripHotelRoom)throws Exception{
            gotripHotelRoom.setCreationDate(new Date());
            return gotripHotelRoomMapper.insertGotripHotelRoom(gotripHotelRoom);
    }

    @Override
    public Integer updateGotripHotelRoom(GotripHotelRoom gotripHotelRoom)throws Exception{
        gotripHotelRoom.setModifyDate(new Date());
        return gotripHotelRoomMapper.updateGotripHotelRoom(gotripHotelRoom);
    }

    @Override
    public Integer deleteGotripHotelRoomById(Long id)throws Exception{
        return gotripHotelRoomMapper.deleteGotripHotelRoomById(id);
    }

    @Override
    public Page<GotripHotelRoom> queryGotripHotelRoomPageByMap(Map<String,Object> param,Integer pageNo,Integer pageSize)throws Exception{
        Integer total = gotripHotelRoomMapper.getGotripHotelRoomCountByMap(param);
        pageNo = EmptyUtils.isEmpty(pageNo) ? Constants.DEFAULT_PAGE_NO : pageNo;
        pageSize = EmptyUtils.isEmpty(pageSize) ? Constants.DEFAULT_PAGE_SIZE : pageSize;
        Page page = new Page(pageNo, pageSize, total);
        param.put("beginPos", page.getBeginPos());
        param.put("pageSize", page.getPageSize());
        List<GotripHotelRoom> gotripHotelRoomList = gotripHotelRoomMapper.getGotripHotelRoomListByMap(param);
        page.setRows(gotripHotelRoomList);
        return page;
    }

}
