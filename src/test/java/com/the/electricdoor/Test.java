package com.the.electricdoor;

import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;

public class Test {
    public static void main(String[] args) {
        // 随机产生UUID
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);
        
        // MD5加密生成签名
        String md5 = DigestUtils.md5Hex("words");
        System.out.println(md5);
    }
}
