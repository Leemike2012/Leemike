package com.the.electricdoor.service;

import com.the.electricdoor.Entity.HeartBeat;
import com.the.electricdoor.utils.Tokenreturn;
import org.springframework.stereotype.Service;

import java.text.ParseException;


@Service
public interface HeartBeatService {
   
    String getSecretKey(String hotel,String room);

    String md5SignatureCreate(String secretKey);

    Boolean checkMd5Signature(String md5Signature,String hotel,String room);

    Boolean checkTimestamp(String timestamp) throws ParseException;

    
    String createToken();

    Integer saveToken(String token,String hotel,String room);

    String getToken(String hotel,String room);

    Integer updateTimestamp(String hotel,String room);

    Tokenreturn initialHeartbeatProcess(HeartBeat heartBeat) throws ParseException;
    //接口类，先定义

    Tokenreturn regularHeartbeatProcess(HeartBeat heartBeat) throws ParseException;

}
