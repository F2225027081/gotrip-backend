package com.ytzl.gotrip.controller;

import com.ytzl.gotrip.model.GotripHotelRoom;
import com.ytzl.gotrip.service.GotripHotelRoomService;
import com.ytzl.gotrip.utils.common.Dto;
import com.ytzl.gotrip.utils.common.DtoUtil;
import com.ytzl.gotrip.vo.hotelroom.SearchHotelRoomVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(description = "酒店房间控制器")
@RequestMapping("/api/hotelroom")
public class GotripHotelRoomController {

    @Resource
    private GotripHotelRoomService gotripHotelRoomService;

    @ApiOperation(value = "查询酒店房间列表")
    @PostMapping("/queryhotelroombyhotel")
    public Dto queryHotelRoomByHotel(@RequestBody SearchHotelRoomVO searchHotelRoomVO) throws Exception {
        List<List<GotripHotelRoom>> resultList =  gotripHotelRoomService.queryHotelRoomByHotel(searchHotelRoomVO);
        return DtoUtil.returnDataSuccess(resultList);
    }
}
