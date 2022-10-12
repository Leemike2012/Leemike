package com.the.electricdoor.controller;

import cn.hutool.core.util.StrUtil;
import com.the.electricdoor.Entity.HeartBeat;
import com.the.electricdoor.service.HeartBeatService;
import com.the.electricdoor.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/heartbeat")
public class HeartBeatController {
    @Autowired
    private HeartBeatService heartBeatService;
    @PostMapping
    public ResponseUtils heartbeat(@RequestBody HeartBeat heartBeat){
        /**判断token是否为空*/
        if (StrUtil.isNotBlank(heartBeat.getToken())){
            /**首次心跳请求流程*/
            /**1、根据旅店uuid和房间号, 从缓存中拿到密钥: `hget 旅店uuid+房间号 key` (如果用uuid和房间号定位不到房间数据, 则认为是恶意攻击)*/
            String secretKey = heartBeatService.getSecretKey(heartBeat.getHotel(),heartBeat.getRoom());
            /**2.根据签名规则, 用请求提供的参数和从缓存中拿到的密钥, 生成md5签名. */
            String md5 = heartBeatService.md5SignatureCreate(secretKey);
            /**3.校验md5签名，如果服务端签名和请求中的签名一致, 继续往下执行逻辑. 不一致则结束处理*/
            if (heartBeatService.checkMd5Signature(md5)){
                /**校验Md5签名成功*/
                /**4.请求的时间戳参数和当前时间比对, 如果超过了超时设定, 则不进行后续处理.*/

            }else {
            }
        }else {
            /**定期循环心跳请求流程*/
        }
        return ResponseUtils.success("");
    }
}
