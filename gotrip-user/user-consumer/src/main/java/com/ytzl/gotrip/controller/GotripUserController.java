package com.ytzl.gotrip.controller;

import com.ytzl.gotrip.model.GotripUser;
import com.ytzl.gotrip.rpc.service.GotripUserService;
import com.ytzl.gotrip.utils.common.Dto;
import com.ytzl.gotrip.utils.common.DtoUtil;
import com.ytzl.gotrip.vo.userinfo.ItripUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
@Api(description = "用户模块控制器")
public class GotripUserController {
    @Resource
    private GotripUserService gotripUserService;
    // value 简单描述 notes 详细描述
    @ApiOperation(value = "根据用户Code查询用户信息",
            notes = "根据用户Code查询用户信息  \n  "+
                    "错误码:  \n  "+
                    "     30003:参数不能为空")
        @GetMapping("/ckusr")
    public Dto ckusr(
                @ApiParam(value = "登录账号")
                @RequestParam("name")String name) throws Exception {
        GotripUser gotripUser = gotripUserService.findByUserCode(name);

        return DtoUtil.returnDataSuccess(gotripUser);
    }






    @ApiOperation(value = "手机号注册")
    @PostMapping("/registerbyphone")
    public Dto registerByPhone(@RequestBody ItripUserVO itripUserVO) throws Exception {

        gotripUserService.registerByPhone(itripUserVO);
        return DtoUtil.returnDataSuccess("注册成功");
    }


    @ApiOperation(value = "手机号激活")
    @PutMapping("/validatephone")
    public Dto validatephone(@ApiParam(value = "登录手机号")
                             @RequestParam String user,
                             @ApiParam(value = "短信验证码")
                             @RequestParam String code) throws Exception {

        //手机号激活
        gotripUserService.validatePhone(user,code);
        return DtoUtil.returnDataSuccess("激活成功");
    }











    @ApiOperation(value="邮箱注册")
    @PostMapping("/doregister")
    public Dto doregister(@RequestBody ItripUserVO userVO) throws Exception {
        gotripUserService.registerByMail(userVO);
        return DtoUtil.returnDataSuccess("注册成功!");
    }



    @ApiOperation(value="邮箱激活")
    @PutMapping("/activate")
    public Dto activate(@ApiParam(value = "邮箱")
                            @RequestParam String user,
                            @ApiParam(value = "验证码")
                            @RequestParam String code) throws Exception {
        gotripUserService.validatemail(user,code);
        return DtoUtil.returnDataSuccess("激活成功");
    }






}
