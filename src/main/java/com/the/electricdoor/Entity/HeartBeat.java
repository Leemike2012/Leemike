package com.the.electricdoor.Entity;

import lombok.Data;

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
