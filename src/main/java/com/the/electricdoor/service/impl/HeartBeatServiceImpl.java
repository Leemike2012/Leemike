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

    @Override
    public Boolean checkTimestamp(String timestamp) {
        return null;
    }

    @Override
    public String createToken() {
        return "ABC";
    }

    @Override
    public Integer saveToken(String token, String hotel, String room) {
        return null;
    }

    @Override
    public String getToken(String hotel, String room) {
        return null;
    }

    @Override
    public Integer updateTimestamp(String hotel, String room) {
        return null;
    }
}
