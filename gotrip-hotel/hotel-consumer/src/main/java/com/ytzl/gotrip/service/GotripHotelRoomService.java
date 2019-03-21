package com.ytzl.gotrip.service;

import com.ytzl.gotrip.model.GotripHotelRoom;
import com.ytzl.gotrip.vo.hotelroom.SearchHotelRoomVO;

import java.util.List;

public interface GotripHotelRoomService {
    /**
     * 根据参数查询酒店房间(有房)列表
     * @param searchHotelRoomVO
     * @return 有房的房间列表
     */
    public List<List<GotripHotelRoom>> queryHotelRoomByHotel(SearchHotelRoomVO searchHotelRoomVO) throws Exception;
}
