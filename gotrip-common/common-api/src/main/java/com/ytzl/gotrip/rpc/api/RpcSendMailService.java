package com.ytzl.gotrip.rpc.api;

/**
 * 发送邮件
 */
public interface RpcSendMailService {

    /**
     *
     * @param receiveMail 接收者邮箱
     */
    public void emailMessage(String receiveMail,String code);

}
