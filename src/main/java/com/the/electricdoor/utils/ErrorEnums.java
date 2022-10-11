package com.the.electricdoor.utils;

public enum ErrorEnums implements ErrorCode {
    SUCCESS("200", "成功!"),
    ID_NOT_EXIST("600","传入ID不存在"),
    NO_DATA_FOUND("601","未查询到数据"),
    PARAMS_ERROR("602","传入参数错误"),
    MD5_SIGNATURE_CHECK_FAILED("700","MD5签名校验失败");


    private String statusCode;
    private String statusMessage;

    ErrorEnums(String statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    @Override
    public String getStatusCode() {
        return statusCode;
    }

    @Override
    public String getStatusMessage() {
        return statusMessage;
    }
}

