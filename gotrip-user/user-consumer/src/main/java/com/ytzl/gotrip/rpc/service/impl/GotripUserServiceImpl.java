package com.ytzl.gotrip.rpc.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ytzl.gotrip.ext.utils.RedisUtils;
import com.ytzl.gotrip.model.GotripUser;
import com.ytzl.gotrip.rpc.api.RpcGotripUserService;
import com.ytzl.gotrip.rpc.api.RpcSendMailService;
import com.ytzl.gotrip.rpc.api.RpcSendMessageService;
import com.ytzl.gotrip.rpc.service.GotripUserService;
import com.ytzl.gotrip.utils.common.Constants;
import com.ytzl.gotrip.utils.common.DigestUtil;
import com.ytzl.gotrip.utils.common.EmptyUtils;
import com.ytzl.gotrip.utils.common.ErrorCode;
import com.ytzl.gotrip.utils.exception.GotripException;
import com.ytzl.gotrip.vo.userinfo.ItripUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@Service
public class GotripUserServiceImpl implements GotripUserService {
    private Logger LOG = LoggerFactory.getLogger(GotripUserServiceImpl.class);

    @Reference
    private RpcGotripUserService rpcGotripUserService;

    @Reference
    private RpcSendMessageService rpcSendMessageService;

    @Reference(timeout = 10000,retries = 0)
    private RpcSendMailService rpcSendMailService;

    @Resource
    private RedisUtils redisUtils;










    @Override
    public GotripUser findByUserCode(String userCode) throws Exception {
        //校验数据
        if (EmptyUtils.isEmpty(userCode)) {
            throw new GotripException("用户Code不能为空！", ErrorCode.AUTH_PARAMETER_ERROR);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("userCode", userCode);
        List<GotripUser> gotripUserList = rpcGotripUserService.getGotripUserListByMap(params);

            /*if(EmptyUtils.isEmpty(gotripUserList)){
                throw new GotripException("登录账号不存在！",ErrorCode.AUTH_PARAMETER_ERROR);
            }
        return gotripUserList.get(0);*/
        return EmptyUtils.isEmpty(gotripUserList) ? null : gotripUserList.get(0);
    }











    @Override
    public void registerByPhone(ItripUserVO itripUserVO) throws Exception {
        checkRegisterData(itripUserVO);
        //验证手机号
        if (!validPhone(itripUserVO.getUserCode())) {
            throw new GotripException("手机号格式不正确", ErrorCode.AUTH_PARAMETER_ERROR);
        }

        //判断用户是否存在
        GotripUser user = this.findByUserCode(itripUserVO.getUserCode());
        if (EmptyUtils.isNotEmpty(user)) {
            throw new GotripException("用户已存在", ErrorCode.AUTH_USER_ALREADY_EXISTS);
        }


        //构建用户信息
        GotripUser gotripUser = new GotripUser();
        BeanUtils.copyProperties(itripUserVO, gotripUser);
        gotripUser.setActivated(Constants.UserActivateStatus.USER_ACTIVATE_DISABLE);
        //密码加密
        String md5UserPassword = DigestUtil.hmacSign(gotripUser.getUserPassword());
        gotripUser.setUserPassword(md5UserPassword);


        //数据入库
        Integer resultSize = rpcGotripUserService.insertGotripUser(gotripUser);
        //发送短信验证码
        //构建四位验证码
        int code = DigestUtil.randomCode();
        rpcSendMessageService.sendMessage(gotripUser.getUserCode(), "1", "" + code);
        //将验证码保存到redis中
        String key = Constants.RedisKeyPrefix.ACTIVATION_MOBILE_PREFIX + gotripUser.getUserCode();
        redisUtils.set(key, "" + code, 60 * 3);
    }


    //邮箱激活
    @Override
    public void validatePhone(String user, String code) throws Exception {
        //验证手机号格式是否正确

        if (!validPhone(user)) {
            throw new GotripException("请输入正确手机号！", ErrorCode.AUTH_PARAMETER_ERROR);
        }


        GotripUser gotripUser = this.findByUserCode(user);
        //验证用户是否存在
        if (EmptyUtils.isEmpty(gotripUser)) {
            throw new GotripException("用户不存在！", ErrorCode.AUTH_PARAMETER_ERROR);
        }

        //获取Redis中存储的短信验证码
        String key = Constants.RedisKeyPrefix.ACTIVATION_MOBILE_PREFIX + user;
        String cacheCode = (String) redisUtils.get(key);

        if (EmptyUtils.isEmpty(cacheCode) || !cacheCode.equals(code)) {
            throw new GotripException("验证码以失效", ErrorCode.AUTH_PARAMETER_ERROR);
        }

        //激活用户
        gotripUser.setActivated(Constants.UserActivateStatus.USER_ACTIVATE_ENABLE);
        gotripUser.setUserType(0);
        gotripUser.setFlatID(gotripUser.getId());
        rpcGotripUserService.updateGotripUser(gotripUser);
        LOG.info("----->  用户[{}]激活成功!", user);
    }



    /**
     * 校验注册数据
     *
     * @param itripUserVO 注册用户信息
     * @throws GotripException
     */
    private void checkRegisterData(ItripUserVO itripUserVO) throws GotripException {
        //数据校验
        if (EmptyUtils.isEmpty(itripUserVO)) {
            throw new GotripException("请传递参数", ErrorCode.AUTH_PARAMETER_ERROR);
        }

        if (EmptyUtils.isEmpty(itripUserVO.getUserCode())) {
            throw new GotripException("用户账号不能为空", ErrorCode.AUTH_PARAMETER_ERROR);
        }

        if (EmptyUtils.isEmpty(itripUserVO.getUserName())) {
            throw new GotripException("用户昵称不能为空", ErrorCode.AUTH_PARAMETER_ERROR);
        }

        if (EmptyUtils.isEmpty(itripUserVO.getUserPassword())) {
            throw new GotripException("用户密码不能为空", ErrorCode.AUTH_PARAMETER_ERROR);
        }
    }


    /**
     * 合法E-mail地址：
     * 1. 必须包含一个并且只有一个符号“@”
     * 2. 第一个字符不得是“@”或者“.”
     * 3. 不允许出现“@.”或者.@
     * 4. 结尾不得是字符“@”或者“.”
     * 5. 允许“@”前的字符中出现“＋”
     * 6. 不允许“＋”在最前面，或者“＋@”
     */
    private boolean validEmail(String email) {

        String regex = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
        return Pattern.compile(regex).matcher(email).find();
    }

    /**
     * 验证是否合法的手机号
     *
     * @param phone
     * @return
     */
    private boolean validPhone(String phone) {
        String regex = "^1[3578]{1}\\d{9}$";
        return Pattern.compile(regex).matcher(phone).find();
    }











    /*******************************邮件操作***************************************/


    @Override
    public void registerByMail(ItripUserVO itripUserVO) throws Exception {
        //验证用户邮箱账号密码不能为空
        checkRegisterData(itripUserVO);


        //验证邮箱格式    true:格式正确    false:格式不正确
        if (!validEmail(itripUserVO.getUserCode())) {
            //不正确
            throw new  GotripException("邮箱格式不正确!",ErrorCode.AUTH_PARAMETER_ERROR);
        }


        //根据Code判断该邮箱是否存在
        GotripUser user = this.findByUserCode(itripUserVO.getUserCode());

        //验证用户是否存在: 判断User是否不为空
        if(EmptyUtils.isNotEmpty(user)){
            //不为空
            throw new GotripException("该邮箱已被注册！",ErrorCode.AUTH_PARAMETER_ERROR);
        }

        //构建用户信息
        GotripUser gotripUser = new GotripUser();
        BeanUtils.copyProperties(itripUserVO,gotripUser);
        gotripUser.setActivated(Constants.UserActivateStatus.USER_ACTIVATE_DISABLE);

        //密码加密
        String md5UserPassword = DigestUtil.hmacSign(gotripUser.getUserPassword());
        gotripUser.setUserPassword(md5UserPassword);

        //数据入库
        Integer resultSize = rpcGotripUserService.insertGotripUser(gotripUser);

        //发送邮箱验证码
        //构建四位验证码
        int code = DigestUtil.randomCode();



        //生成redis中存入的Key
        String key = Constants.RedisKeyPrefix.ACTIVATION_MAIL_PREFIX + gotripUser.getUserCode();
        //将key和验证码存入redis中,并设置过期时间
        redisUtils.set(key,"" + code, 60 * 3);

        rpcSendMailService.emailMessage(gotripUser.getUserCode(),""+code);


    }


    //邮箱激活
    @Override
    public void validatemail(String mail, String code) throws Exception {
        //验证邮箱格式是否正确
        if (!validEmail(mail)) {
            throw new GotripException("请输入正确邮箱",ErrorCode.AUTH_PARAMETER_ERROR);
        }

        //验证用户是否存在
        GotripUser gotripUser = this.findByUserCode(mail);
        if (EmptyUtils.isEmpty(gotripUser)) {
            throw new GotripException("用户不存在!",ErrorCode.AUTH_PARAMETER_ERROR);
        }

        //从redis中获取Key
        String key = Constants.RedisKeyPrefix.ACTIVATION_MAIL_PREFIX + mail;
        String cacheCode = (String)redisUtils.get(key);

        if (EmptyUtils.isEmpty(cacheCode) || !cacheCode.equals(code)) {
            throw new GotripException("验证码以失效", ErrorCode.AUTH_PARAMETER_ERROR);
        }

        //激活用户
        gotripUser.setActivated(Constants.UserActivateStatus.USER_ACTIVATE_ENABLE);
        gotripUser.setUserType(0);
        gotripUser.setFlatID(gotripUser.getId());
        rpcGotripUserService.updateGotripUser(gotripUser);
        LOG.info("----->  用户[{}]激活成功!", mail);
    }






}
