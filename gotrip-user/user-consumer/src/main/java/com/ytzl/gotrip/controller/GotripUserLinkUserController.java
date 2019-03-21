package com.ytzl.gotrip.controller;

import com.ytzl.gotrip.model.GotripUserLinkUser;
import com.ytzl.gotrip.rpc.service.GotripUserLinkUserService;
import com.ytzl.gotrip.utils.common.Dto;
import com.ytzl.gotrip.utils.common.DtoUtil;
import com.ytzl.gotrip.vo.userinfo.ItripAddUserLinkUserVO;
import com.ytzl.gotrip.vo.userinfo.ItripModifyUserLinkUserVO;
import com.ytzl.gotrip.vo.userinfo.ItripSearchUserLinkUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/userinfo")
@Api(description = "主业务模块API")
public class GotripUserLinkUserController {

    @Resource
    private GotripUserLinkUserService gotripUserLinkUserService;


    @ApiOperation(value = "查询常用联系人")
    @PostMapping("/queryuserlinkuser")
    public Dto  queryuserlinkuser(@RequestBody ItripAddUserLinkUserVO itripAddUserLinkUserVO,
                                  @ApiParam(value = "令牌") @RequestHeader("token") String token,
                                  @RequestHeader(value = "user-agent",required = false)String agent) throws Exception {

        GotripUserLinkUser gotripUserLinkUser = new GotripUserLinkUser();

        //把vo类中的参数复制到实体类中
        BeanUtils.copyProperties(itripAddUserLinkUserVO,gotripUserLinkUser);

        //调用业务层方法
        List<GotripUserLinkUser> queryuserlinkuser = gotripUserLinkUserService.queryuserlinkuser(token, agent, gotripUserLinkUser);


        return DtoUtil.returnDataSuccess(queryuserlinkuser);
    }


    @ApiOperation(value = "新增常用联系人")
    @PostMapping("/adduserlinkuser")
    public Dto adduserlinkuser(@RequestBody ItripSearchUserLinkUserVO itripSearchUserLinkUserVO,
                               @ApiParam(value = "令牌")@RequestHeader("token") String token,
                               @RequestHeader(value = "user-agent",required = false)String agent) throws Exception {


        GotripUserLinkUser gotripUserLinkUser = new GotripUserLinkUser();
        BeanUtils.copyProperties(itripSearchUserLinkUserVO,gotripUserLinkUser);
        Integer integer = gotripUserLinkUserService.insertGotripUserLinkUser(token, agent, gotripUserLinkUser);

        return DtoUtil.returnDataSuccess(integer);
    }


    @ApiOperation(value = "修改常用联系人")
    @PostMapping("/modifyuserlinkuser")
    public Dto modifyuserlinkuser(@RequestBody ItripModifyUserLinkUserVO itripModifyUserLinkUserVO,
                                  @ApiParam(value = "令牌")@RequestHeader("token") String token,
                                  @RequestHeader(value = "user-agent",required = false) String agent) throws Exception {

        GotripUserLinkUser gotripUserLinkUser = new GotripUserLinkUser();
        BeanUtils.copyProperties(itripModifyUserLinkUserVO,gotripUserLinkUser);
        gotripUserLinkUserService.updateGotripUserLinkUser(token,agent,gotripUserLinkUser);
        return  DtoUtil.returnDataSuccess("修改成功");
    }


    @ApiOperation(value = "删除常用联系人")
    @GetMapping("/deluserlinkuser")
    public Dto deluserlinkuser(@ApiParam(value = "删除常用联系人")@RequestParam long ids) throws Exception {

        gotripUserLinkUserService.deleteGotripUserLinkUserById(ids);
        return DtoUtil.returnDataSuccess("删除成功!");
    }
}

