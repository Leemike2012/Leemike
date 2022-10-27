package com.the.electricdoor.service;

import javax.mail.MessagingException;
import java.util.List;

public interface EmailAddressService {
    //取出所有有效的email地址
    List<String> getValidEmailAddress();

    //发送邮件的方法
    void sendEmail(List<String> emailAddressList,String subject,String emailContent) throws MessagingException;
}
