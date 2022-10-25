package com.the.electricdoor.service.impl;

import com.the.electricdoor.Entity.HeartBeat;
import com.the.electricdoor.Entity.LockerKey;
import com.the.electricdoor.mapper.LockerKeyMapper;
import com.the.electricdoor.service.HeartBeatService;
import com.the.electricdoor.utils.Tokenreturn;

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
    public Tokenreturn initialHeartbeatProcess(HeartBeat heartBeat) throws ParseException {
        
        /**1、根据旅店uuid和房间号, 从缓存中拿到密钥: `hget 旅店uuid+房间号 key` (如果用uuid和房间号定位不到房间数据, 则认为是恶意攻击)*/
        String secretKey = getSecretKey(heartBeat.getHotel(),heartBeat.getRoom());
        /**2.根据签名规则, 用请求提供的参数和从缓存中拿到的密钥, 生成md5签名. */
        String md5 = md5SignatureCreate(secretKey);
        /**3.校验md5签名，如果服务端签名和请求中的签名一致, 继续往下执行逻辑. 不一致则结束处理*/
        if (checkMd5Signature(md5,heartBeat.getHotel(),heartBeat.getRoom())){
            /**校验Md5签名成功*/
            /**4.请求的时间戳参数和当前时间比对*/
            if(checkTimestamp(heartBeat.getTimestamp())){
                /**时间戳校验成功*/
                /**5.随机生成UUID作为Token，范围给门锁*/
                String token = createToken();
                /**6.token存入数据库，用于循环发送请求校验，同时存入时间等*/
                saveToken(token,heartBeat.getHotel(),heartBeat.getRoom());
                /**7.返回token给门锁*/
                return Tokenreturn.success(token);
            }else{
                /**超过了超时设定, 则不进行后续处理*/
                return Tokenreturn.error("701","请求超时");
                /**此处两行注释就是相当于对上面的范围错误类型用枚举类进行了同一封装处理，方便修改和复用
                 return ResponseUtils.error(ErrorEnums.TIMEOUT_ERROR.getStatusCode(),
                 ErrorEnums.TIMEOUT_ERROR.getStatusMessage());
                 */
            }
        }else {
            /**校验Md5签名失败，终止请求，返回报错*/
            return Tokenreturn.error("700","MD5签名校验失败");
            /**此处两行注释就是相当于对上面的范围错误类型用枚举类进行了同一封装处理，方便修改和复用
             return ResponseUtils.error(ErrorEnums.MD5_SIGNATURE_CHECK_FAILED.getStatusCode(),
             ErrorEnums.MD5_SIGNATURE_CHECK_FAILED.getStatusMessage());
             */
        }
    }

    @Override
    public Tokenreturn regularHeartbeatProcess(HeartBeat heartBeat) throws ParseException {
        /**1.根据旅店uuid和房间号, 从缓存中拿到密钥*/
        String secretKey = getSecretKey(heartBeat.getHotel(),heartBeat.getRoom());
        /**2.根据签名规则, 用请求提供的参数和从缓存中拿到的密钥, 生成md5签名. */
        String md5 = md5SignatureCreate(secretKey);
        /**3.校验md5签名，如果服务端签名和请求中的签名一致, 继续往下执行逻辑. 不一致则结束处理*/
        if (checkMd5Signature(md5,heartBeat.getHotel(),heartBeat.getRoom())){
            /**校验Md5签名成功*/
            /**4.请求的时间戳参数和当前时间比对*/
            if(checkTimestamp(heartBeat.getTimestamp())){
                /**时间戳校验成功*/
                /**5.因为首次已经生成token，并且存入了数据库，所以能根据hotel和room取出token*/
                String token = getToken(heartBeat.getHotel(), heartBeat.getRoom());
                /**6.取出的token和请求中的token进行比对*/
                if (token.equals(heartBeat.getToken())){
                    /**比对成功*/
                    /**7.用当前系统时间更新掉缓存中的时间戳*/
                    updateTimestamp(heartBeat.getHotel(), heartBeat.getRoom());
                    /**返回服务端确认门锁存活成功*/
                    return Tokenreturn.success("success");
                }else {
                    /**比对失败*/
                    return Tokenreturn.error("702","token匹配失败");
                }
            }else{
                /**超过了超时设定, 则不进行后续处理*/
                return Tokenreturn.error("701","请求超时");
                /**此处两行注释就是相当于对上面的范围错误类型用枚举类进行了同一封装处理，方便修改和复用
                 return ResponseUtils.error(ErrorEnums.TIMEOUT_ERROR.getStatusCode(),
                 ErrorEnums.TIMEOUT_ERROR.getStatusMessage());
                 */
            }
        }else {
            /**校验Md5签名失败，终止请求，返回报错*/
            return Tokenreturn.error("700","MD5签名校验失败");
            /**此处两行注释就是相当于对上面的范围错误类型用枚举类进行了同一封装处理，方便修改和复用
             return ResponseUtils.error(ErrorEnums.MD5_SIGNATURE_CHECK_FAILED.getStatusCode(),
             ErrorEnums.MD5_SIGNATURE_CHECK_FAILED.getStatusMessage());
             */
        }
    }

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
        //规则未知所以自己写了
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
        //差值在5000以内，true，随便写的
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

