package com.the.electricdoor.controller;

import cn.hutool.core.util.StrUtil;
import com.the.electricdoor.Entity.HeartBeat;
import com.the.electricdoor.service.HeartBeatService;
import com.the.electricdoor.utils.Tokenreturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/heartbeat")
public class HeartBeatController {
    @Autowired
    private HeartBeatService heartBeatService;
    
    @PostMapping
    public Tokenreturn heartbeatProcess(@RequestBody HeartBeat heartBeat) throws ParseException {
        //请求第一次来的时候放在controller里面，必须要是http post请求，请求内容是heartbeat实体类
        /**判断token是否为空*/
        if (StrUtil.isNotBlank(heartBeat.getToken())){
            /**首次心跳请求流程*/
            return heartBeatService.initialHeartbeatProcess(heartBeat);
        }else {
            /**定期循环心跳请求流程*/
            return heartBeatService.regularHeartbeatProcess(heartBeat);
        }
    }
}