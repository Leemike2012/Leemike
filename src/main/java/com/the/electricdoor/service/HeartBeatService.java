package com.the.electricdoor.service;

import org.springframework.stereotype.Service;

@Service
public interface HeartBeatService {
    
    String getSecretKey(String hotel,String room);

    String md5SignatureCreate(String secretKey);

    Boolean checkMd5Signature(String md5Signature);
}
