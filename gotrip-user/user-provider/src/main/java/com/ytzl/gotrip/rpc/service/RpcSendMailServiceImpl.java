package com.ytzl.gotrip.rpc.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.ytzl.gotrip.rpc.api.RpcSendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = RpcSendMailService.class)
public class RpcSendMailServiceImpl implements RpcSendMailService {


    @Autowired
    JavaMailSender javaMailSender;

    //发送邮箱验证
    @Override
    public void emailMessage(String receiveMail,String code){



        //建立邮件信息
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //发送者
        mailMessage.setFrom("2225027081@qq.com");
        //接收者
        mailMessage.setTo(receiveMail);
        //发送的标题
        mailMessage.setSubject("Aaa的邮件");
        //发送的内容
        mailMessage.setText(code);
        javaMailSender.send(mailMessage);
    }

}
