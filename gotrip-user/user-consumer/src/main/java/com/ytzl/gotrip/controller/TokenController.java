package com.ytzl.gotrip.controller;

import com.ytzl.gotrip.rpc.service.TokenService;
import com.ytzl.gotrip.utils.common.Dto;
import com.ytzl.gotrip.utils.common.DtoUtil;
import com.ytzl.gotrip.utils.exception.GotripException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.ParseException;

@RestController
@Api(description = "用户认证模块API")
@RequestMapping("/api")
public class TokenController {

    @Resource
    private TokenService tokenService;

    @ApiOperation(value = "置换Token")
    @PostMapping("/retoken")
    public Dto retoken(@ApiParam(value = "令牌") @RequestHeader("token") String token,
                       @ApiParam(hidden = true)
                       @RequestHeader(value = "user-agent",required = false) String agent) throws GotripException, ParseException {

        tokenService.retoken(token,agent);
        return DtoUtil.returnDataSuccess("成功?");
    }


}
