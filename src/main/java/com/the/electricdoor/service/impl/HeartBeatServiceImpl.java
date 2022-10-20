package com.the.electricdoor.service.impl;

import com.the.electricdoor.Entity.LockerKey;
import com.the.electricdoor.mapper.LockerKeyMapper;
import com.the.electricdoor.service.HeartBeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class HeartBeatServiceImpl implements HeartBeatService {
    @Autowired
    private LockerKeyMapper lockerKeyMapper;

    @Override
    public String getSecretKey(String hotel, String room) {
        //拼接旅店Id+房间id
        String hotelRoom = hotel+room;
        //根据拼接后数据获取该条lockerKey记录
        LockerKey lockerKey = lockerKeyMapper.getDataByHotelRoom(hotelRoom);
        if (lockerKey==null){
            //数据获取为空,返回null值交由controller判断（其实应直接做抛出异常处理，由统一异常捕获器捕获）
            return null;
        }else {
            //数据不为空，则返回key值
            return lockerKey.getKey();
        }
    }

    @Override
    public String md5SignatureCreate(String secretKey) {
        String md5Signature = DigestUtils.md5DigestAsHex(secretKey.getBytes(StandardCharsets.UTF_8));
        return md5Signature;
    }

    @Override
    public Boolean checkMd5Signature(String md5Signature,String hotel,String room) {
        String hotelRoom = hotel+room;
        String md5 = lockerKeyMapper.getMd5ByHotelRoom(hotelRoom);
        if (md5Signature.equals(md5)){
            return true;
        }else {
            return false;
        }
    }
    
    @Override
    public Boolean checkTimestamp(String timestamp) throws ParseException {
        Date now =  new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date paramTime = sdf.parse(timestamp);
        //计算请求时间和当前时间差值秒数
        long longNow = now.getTime();
        long longParamTime = paramTime.getTime();
        int diff = (int) ((longNow - longParamTime) / 1000);
        //差值在5000以内，true
        if (diff<5000){
            return true;
        }else {
            return false;
        }
    }
    
    @Override
    public String createToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Integer saveToken(String token, String hotel, String room) {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = sdf.format(now);
        String hotel_room = hotel+room;
        return lockerKeyMapper.updateData(hotel_room,token,timestamp);
    }

    @Override
    public String getToken(String hotel, String room) {
        String hotel_room = hotel+room;
        LockerKey lockerKey = lockerKeyMapper.getDataByHotelRoom(hotel_room);
        if (lockerKey!=null){
            return lockerKey.getToken();
        }else {
            return null;
        }
    }

    //废弃
    @Override
    public Integer updateTimestamp(String hotel, String room) {
        return null;
    }
}

