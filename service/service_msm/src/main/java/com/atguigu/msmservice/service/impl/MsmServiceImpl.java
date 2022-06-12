package com.atguigu.msmservice.service.impl;


import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class MsmServiceImpl implements MsmService {
    private static final String USER = "836585692@qq.com"; // 发件人称号，同邮箱地址
    private static final String PASSWORD = "cnjpmrsnehdlbbde"; // 如果是qq邮箱可以使户端授权码，或者登录密码

    /**
     * @param code       代表验证码
     * @param mailAdress 代表发送去哪个地址
     * @return
     */
    @Override
    public boolean send(String code, String mailAdress) {
        try {
            final Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.qq.com");
            // 发件人的账号
            props.put("mail.user", USER);
            //发件人的密码
            props.put("mail.password", PASSWORD);

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            Session mailSession = Session.getInstance(props, authenticator);
            // 创建邮件消息
            MimeMessage message = new MimeMessage(mailSession);
            // 设置发件人
            String username = props.getProperty("mail.user");
            InternetAddress from = new InternetAddress(username);
            message.setFrom(from);

            // 设置收件人
            InternetAddress toAddress = new InternetAddress(mailAdress);
            message.setRecipient(Message.RecipientType.TO, toAddress);

            // 设置邮件标题
            message.setSubject("谷粒在线教育网站申请注册");

            // 设置邮件的内容体
            message.setContent("您正在申请账号注册，验证码为：" + code + "，5分钟内有效！", "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

