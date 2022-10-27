-- 创建邮件地址表
DROP TABLE IF EXISTS email_address;
CREATE TABLE email_address(
  id INT NOT NULL AUTO_INCREMENT  COMMENT '流水号' ,
  email_address VARCHAR(255)    COMMENT '邮箱地址' ,
  status INT    COMMENT '状态' ,
  type INT    COMMENT '类型' ,
  PRIMARY KEY (id)
)  COMMENT = '邮箱地址表';
