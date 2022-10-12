package com.the.electricdoor.controller;

import cn.hutool.core.util.StrUtil;
import com.the.electricdoor.Entity.HeartBeat;
import com.the.electricdoor.service.HeartBeatService;
import com.the.electricdoor.utils.ErrorEnums;
import com.the.electricdoor.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/heartbeat")
public class HeartBeatController {
    @Autowired
    private HeartBeatService heartBeatService;
    @PostMapping
    public ResponseUtils queryById(@RequestBody HeartBeat heartBeat){
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
                /**4.请求的时间戳参数和当前时间比对*/
                if(heartBeatService.checkTimestamp(heartBeat.getTimestamp())){
                    /**时间戳校验成功*/
                    /**5.随机成成UUID作为Token，范围给门锁*/
                    String token = heartBeatService.createToken();
                    /**6.token存入数据库*/
                    heartBeatService.saveToken(token,heartBeat.getHotel(),heartBeat.getRoom());
                    /**7.返回token给门锁*/
                    return ResponseUtils.success(token);
                }else{
                    /**超过了超时设定, 则不进行后续处理*/
                    return ResponseUtils.error("701","请求超时");
                    /**此处两行注释就是相当于对上面的范围错误类型用枚举类进行了同一封装处理，方便修改和复用
                    return ResponseUtils.error(ErrorEnums.TIMEOUT_ERROR.getStatusCode(),
                            ErrorEnums.TIMEOUT_ERROR.getStatusMessage());
                     */
                }
            }else {
                /**校验Md5签名失败，终止请求，返回报错*/
                return ResponseUtils.error("700","MD5签名校验失败");
                /**此处两行注释就是相当于对上面的范围错误类型用枚举类进行了同一封装处理，方便修改和复用
                return ResponseUtils.error(ErrorEnums.MD5_SIGNATURE_CHECK_FAILED.getStatusCode(),
                        ErrorEnums.MD5_SIGNATURE_CHECK_FAILED.getStatusMessage());
                 */
            }
        }else {
            /**定期循环心跳请求流程*/

            /**1.根据旅店uuid和房间号, 从缓存中拿到密钥*/
            String secretKey = heartBeatService.getSecretKey(heartBeat.getHotel(),heartBeat.getRoom());
            /**2.根据签名规则, 用请求提供的参数和从缓存中拿到的密钥, 生成md5签名. */
            String md5 = heartBeatService.md5SignatureCreate(secretKey);
            /**3.校验md5签名，如果服务端签名和请求中的签名一致, 继续往下执行逻辑. 不一致则结束处理*/
            if (heartBeatService.checkMd5Signature(md5)){
                /**校验Md5签名成功*/
                /**4.请求的时间戳参数和当前时间比对*/
                if(heartBeatService.checkTimestamp(heartBeat.getTimestamp())){
                    /**时间戳校验成功*/
                    /**5.根据hotel和room取出token*/
                    String token = heartBeatService.getToken(heartBeat.getHotel(), heartBeat.getRoom());
                    /**6.取出的token和请求中的token进行比对*/
                    if (token.equals(heartBeat.getToken())){
                        /**比对成功*/
                        /**7.用当前系统时间更新掉缓存中的时间戳*/
                        heartBeatService.updateTimestamp(heartBeat.getHotel(), heartBeat.getRoom());
                        /**返回服务端确认门锁存活成功*/
                        return ResponseUtils.success("success");
                    }else {
                        /**比对失败*/
                        return ResponseUtils.error("702","token匹配失败");
                    }
                }else{
                    /**超过了超时设定, 则不进行后续处理*/
                    return ResponseUtils.error("701","请求超时");
                    /**此处两行注释就是相当于对上面的范围错误类型用枚举类进行了同一封装处理，方便修改和复用
                     return ResponseUtils.error(ErrorEnums.TIMEOUT_ERROR.getStatusCode(),
                     ErrorEnums.TIMEOUT_ERROR.getStatusMessage());
                     */
                }
            }else {
                /**校验Md5签名失败，终止请求，返回报错*/
                return ResponseUtils.error("700","MD5签名校验失败");
                /**此处两行注释就是相当于对上面的范围错误类型用枚举类进行了同一封装处理，方便修改和复用
                 return ResponseUtils.error(ErrorEnums.MD5_SIGNATURE_CHECK_FAILED.getStatusCode(),
                 ErrorEnums.MD5_SIGNATURE_CHECK_FAILED.getStatusMessage());
                 */
            }

        }
    }
}
