package com.the.electricdoor.Entity;

import lombok.Data;

/**
 * @ClassName:HeartBeat
 * @Description:TODO
 * @Author:LeeMike
 * @Date:2022-10-11
 * @Version:1.0
 **/
@Data
public class HeartBeat {
    /** token*/
    String token;
    /** 时间戳格式:YYYYMMDDHHMMSS*/
    String timestamp;
    /** 旅店的uuid*/
    String hotel;
    /** 房间号*/
    String room;
    /** token+hotel+room+时间戳+密钥的MD5*/
    String sign;
}
