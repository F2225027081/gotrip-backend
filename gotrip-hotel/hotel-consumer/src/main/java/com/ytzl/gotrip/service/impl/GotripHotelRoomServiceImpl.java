package com.ytzl.gotrip.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ytzl.gotrip.model.GotripHotelRoom;
import com.ytzl.gotrip.rpc.api.RpcGotripHotelRoomService;
import com.ytzl.gotrip.service.GotripHotelRoomService;
import com.ytzl.gotrip.utils.common.DateUtil;
import com.ytzl.gotrip.utils.common.EmptyUtils;
import com.ytzl.gotrip.utils.common.ErrorCode;
import com.ytzl.gotrip.utils.exception.GotripException;
import com.ytzl.gotrip.vo.hotelroom.SearchHotelRoomVO;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Service("gotripHotelRoomService")
public class GotripHotelRoomServiceImpl implements GotripHotelRoomService {
    @Reference
    private RpcGotripHotelRoomService rpcGotripHotelRoomService;

    @Override
    public List<List<GotripHotelRoom>> queryHotelRoomByHotel(SearchHotelRoomVO searchHotelRoomVO) throws Exception {
        //校验数据
        checkSearchHotelRoomVOData(searchHotelRoomVO);

        //查询房间列表
        //日期集合 判断入住时间到退房时间 期间是否有房
        List<Date>  dateList = DateUtil.getBetweenDates(searchHotelRoomVO.getStartDate(), searchHotelRoomVO.getEndDate());

        String json = JSON.toJSONString(searchHotelRoomVO);
        Map<String,Object> params = JSON.parseObject(json, Map.class);
        params.put("dateList", dateList);
        List<GotripHotelRoom> hotelRoomList = rpcGotripHotelRoomService.getGotripHotelRoomListByMap(params);
        //[[{}],[{}],[{}]]
        List<List<GotripHotelRoom>> resultList = new ArrayList<>();
        for (GotripHotelRoom gotripHotelRoom : hotelRoomList) {
            List<GotripHotelRoom> nodeList = new ArrayList<>();
            nodeList.add(gotripHotelRoom);
            resultList.add(nodeList);
        }

        return resultList;
    }

    /**
     * 校验数据
     * @param searchHotelRoomVO
     * @throws Exception
     */
    private void checkSearchHotelRoomVOData(SearchHotelRoomVO searchHotelRoomVO) throws Exception{
        if (EmptyUtils.isEmpty(searchHotelRoomVO)) {
            throw new GotripException("酒店参数不能够为空", ErrorCode.AUTH_PARAMETER_ERROR);
        }
        if (EmptyUtils.isEmpty(searchHotelRoomVO.getHotelId())) {
            throw new GotripException("酒店Id不能够为空", ErrorCode.AUTH_PARAMETER_ERROR);
        }
        if (EmptyUtils.isEmpty(searchHotelRoomVO.getStartDate()) || EmptyUtils.isEmpty(searchHotelRoomVO.getEndDate())) {
            throw new GotripException("入住时间和退房时间不能为空", ErrorCode.AUTH_PARAMETER_ERROR);
        }
        //入住时间不能大于退房时间
        if(searchHotelRoomVO.getStartDate().getTime()>
                searchHotelRoomVO.getEndDate().getTime()){
            throw new GotripException("入住时间不能大于退房时间",ErrorCode.AUTH_PARAMETER_ERROR);
        }
    }



}
