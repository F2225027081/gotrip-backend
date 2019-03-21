package com.ytzl.gotrip.controller;

import com.ytzl.gotrip.service.GotripOrderService;
import com.ytzl.gotrip.utils.common.Dto;
import com.ytzl.gotrip.vo.order.ValidateRoomStoreVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Api(description = "订单控制器")
@RequestMapping("/api/hotelorder")
public class GotripOrderController {

    @Resource
    private GotripOrderService gotripOrderService;

    @ApiOperation(value = "下单前获取预订信息")
    @PostMapping("/getpreorderinfo")
    public Dto getPreOrderInfo(@RequestBody ValidateRoomStoreVO validateRoomStoreVO) {

        return null;
    }





}
