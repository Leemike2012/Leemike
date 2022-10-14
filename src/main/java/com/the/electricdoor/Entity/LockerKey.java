package com.the.electricdoor.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("locker_key")
@Data
public class LockerKey {
    /** 自增id */
    @TableId
    private Integer id ;
    /** 旅店id+房间id */
    private String hotelRoom ;
    /** 密钥 */
    private String Key ;
    /** token */
    private String Token ;
    /** 时间戳 */
    private String Timestamp ;
}
