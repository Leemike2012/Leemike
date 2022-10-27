package com.the.electricdoor.service.impl;

import com.the.electricdoor.mapper.EmailAddressMapper;
import com.the.electricdoor.service.EmailAddressService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;


public class EmailAddressServiceImpl implements EmailAddressService {
    private EmailAddressMapper emailAddressMapper;
    @Autowired
    public void setEmailAddressMapper(EmailAddressMapper emailAddressMapper){this.emailAddressMapper = emailAddressMapper;}

    //此处填写的就是用来发送邮件的邮箱的邮件服务器地址，邮箱地址以及密码
    private static String HOST="suibian@gmail.com";
    private static String USERNAME = "suibian@gmail.com";
    private static String PASSWORD = "123456";

    
    @Override
    public List<String> getValidEmailAddress() {
        return emailAddressMapper.getEmailAddress();
    }

    @Override
    public void sendEmail(List<String> emailAddressList, String subject, String emailContent) throws MessagingException {
        //创建配置文件
        Properties props = new Properties();
        //设置发送时遵从SMTP协议
        props.setProperty("mail.transport.protocol", "SMTP");
        /*
         * 发送邮件的域名
         * smtp.xx.com
         */
        props.setProperty("mail.host", HOST);
        //设置用户的认证方式auth
        props.setProperty("mail.smtp.auth", "true");
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                //return new PasswordAuthentication("用户名", "密码");
                //注意！！！qq邮箱需要去qq邮箱的设置中获取授权码，并将授权码作为密码来填写
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        };
        //创建session域
        Session session = Session.getInstance(props, auth);
        //轮流发送邮件
        for (int i = 0; i < emailAddressList.size(); i++) {
            Message message = new MimeMessage(session);
            //设置邮件发送者,与PasswordAuthentication中的邮箱一致即可
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddressList.get(i)));
            //设置邮件主题
            message.setSubject(subject);
            //设置邮件内容
            message.setContent(emailContent, "text/html;charset=utf-8");
            //发送邮件
            Transport.send(message);
        }
    }
}
