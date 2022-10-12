package com.the.electricdoor.service.impl;

import com.the.electricdoor.service.HeartBeatService;
import org.springframework.stereotype.Service;


@Service
public class HeartBeatServiceImpl implements HeartBeatService {
    @Override
    public String getSecretKey(String hotel, String room) {
        return null;
    }

    @Override
    public String md5SignatureCreate(String secretKey) {
        return null;
    }

    @Override
    public Boolean checkMd5Signature(String md5Signature) {
        return true;
    }
}
