package com.the.electricdoor.utils;

import lombok.Data;

@Data
public class Tokenreturn {
    private String statusCode;
    private String statusMessage;
    private Object data;

    public static Tokenreturn success(Object object){
        Tokenreturn ro = new Tokenreturn();
        ro.statusCode= ErrorEnums.SUCCESS.getStatusCode();
        ro.statusMessage =ErrorEnums.SUCCESS.getStatusMessage();
        ro.data= object;
        return ro;
    }

    public static Tokenreturn error(String statusCode,String statusMessage){
        Tokenreturn ro = new Tokenreturn();
        ro.statusCode= statusCode;
        ro.statusMessage = statusMessage;
        return ro;
    }
}