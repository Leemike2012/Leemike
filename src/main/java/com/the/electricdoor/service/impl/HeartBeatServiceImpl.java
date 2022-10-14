package com.the.electricdoor.service.impl;

import com.the.electricdoor.service.HeartBeatService;
import org.springframework.stereotype.Service;

@Service
public class HeartBeatServiceImpl implements HeartBeatService {
    @Autowired
    private LockerKeyMapper lockerKeyMapper;

    @Override
    public String getSecretKey(String hotel, String room) {
        String hotelRoom = hotel+room;
        LockerKey lockerKey = lockerKeyMapper.getDataByHotelRoom(hotelRoom);
        if (lockerKey==null){
            return null;
        }else {
            return lockerKey.getKey();
        }
    }
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
