package com.ytzl.gotrip.rpc.api;

/**
 * 短信发送Rpc
 */
public interface RpcSendMessageService {

    /**
     * 发送短信
     *
     * @param phone 手机号,多手机号用[,]分隔
     * @param templateId 模板Id，未上线应用填写 1 (官方提供，固定模板)
     * @param code 短信验证码
     */
    public void sendMessage(String phone,String templateId,String code);



}
