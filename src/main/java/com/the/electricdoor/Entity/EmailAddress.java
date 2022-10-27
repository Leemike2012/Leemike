package com.the.electricdoor.Entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class EmailAddress {
    /** 流水号 */
    @TableId
    private Integer id ;
    /** 邮箱地址 */
    private String emailAddress ;
    /** 状态 */
    private Integer status ;
    /** 类型 */
    private Integer type ;
}
