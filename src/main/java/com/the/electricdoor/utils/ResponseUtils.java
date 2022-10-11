package com.the.electricdoor.utils;

import lombok.Data;

/**
 * @ClassName:RespsonseUtils
 * @Description:TODO
 * @Author:Yang
 * @Date:2022-08-28
 * @Version:1.0
 **/
@Data
public class ResponseUtils {
    private String statusCode;
    private String statusMessage;
    private Object data;

    public static ResponseUtils success(Object object){
        ResponseUtils ro = new ResponseUtils();
        ro.statusCode= ErrorEnums.SUCCESS.getStatusCode();
        ro.statusMessage =ErrorEnums.SUCCESS.getStatusMessage();
        ro.data= object;
        return ro;
    }

    public static ResponseUtils error(String statusCode,String statusMessage){
        ResponseUtils ro = new ResponseUtils();
        ro.statusCode= statusCode;
        ro.statusMessage = statusMessage;
        return ro;
    }
}
