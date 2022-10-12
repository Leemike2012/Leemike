package com.the.electricdoor.service;

import org.springframework.stereotype.Service;

@Service
public interface HeartBeatService {
   
    String getSecretKey(String hotel,String room);

    String md5SignatureCreate(String secretKey);

    Boolean checkMd5Signature(String md5Signature);

    Boolean checkTimestamp(String timestamp);

    
    String createToken();

    Integer saveToken(String token,String hotel,String room);

    String getToken(String hotel,String room);

    Integer updateTimestamp(String hotel,String room);

}
