package com.the.electricdoor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.the.electricdoor.Entity.EmailAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EmailAddressMapper extends BaseMapper<EmailAddress> {
    //取出所有生效的邮箱地址
    @Select("SELECT email_address FROM email_address WHERE status = 1")
    List<String> getEmailAddress();
}
