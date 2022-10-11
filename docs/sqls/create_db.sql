create database electricdoor;

use electricdoor;

-- 访客账户表
CREATE TABLE IF NOT EXISTS acount 
(
    acount_name VARCHAR(20) NOT NULL PRIMARY KEY COMMENT '用户名',
    acount_password VARCHAR(10) NOT NULL COMMENT '用户密码',
    nick_name VARCHAR(10) COMMENT '用户登陆后的昵称',
    sex TINYINT COMMENT '性别. 1:男, 2:女',
    create_time DATETIME COMMENT '数据创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 旅店表
CREATE TABLE IF NOT EXISTS hotel
(
    hotel_uuid VARCHAR(36) NOT NULL PRIMARY KEY COMMENT '旅店数据在库中的主键uuid',
    name VARCHAR(25) NOT NULL COMMENT '旅店名称',
    address VARCHAR(50) NOT NULL COMMENT '旅店地址',
    description VARCHAR(100) COMMENT '旅店描述',
    create_time DATETIME COMMENT '数据创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 房间表
CREATE TABLE IF NOT EXISTS room
(
    room_id VARCHAR(4) NOT NULL COMMENT '房间门牌号',
    hotel_uuid VARCHAR(36) NOT NULL COMMENT '房间所属的旅店的主键',
    healthy TINYINT NOT NULL COMMENT '房间的状态. 1:正常运行中, 0:已损坏, 2:清扫中, 3:尚未上架',
    tatami_rooms TINYINT NOT NULL COMMENT '房间内和室的个数',
    western_rooms TINYINT NOT NULL COMMENT '房间内洋室的个数',
    has_dining TINYINT NOT NULl COMMENT '是否有餐厅. 1:有, 0:没有',
    has_kitchen TINYINT NOT NULL COMMENT '是否有厨房. 1: 有, 0: 没有',
    has_aircondition TINYINT NOT NULL COMMENT '是否有空调. 1: 有, 0: 没有',
    description VARCHAR(100) COMMENT '房间描述',
    seed_key VARCHAR(36) NOT NULL COMMENT '随机初始化种子, 作为门锁心跳的密钥',
    create_time DATETIME COMMENT '数据创建时间',
    PRIMARY KEY (hotel_uuid, room_id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 订单表
CREATE TABLE IF NOT EXISTS guest_order
(
    order_id VARCHAR(36) NOT NULL PRIMARY KEY COMMENT '订单号',
    room_id VARCHAR(4) NOT NULL COMMENT '房间门牌号',
    hotel_uuid VARCHAR(36) NOT NULL COMMENT '旅店数据在库中的主键',
    hotel_address VARCHAR(50) NOT NULL COMMENT '旅店地址',
    start_date DATETIME NOT NULL COMMENT '订单开始时间',
    end_date DATETIME NOT NULL COMMENT '订单结束时间',
    create_time DATETIME COMMENT '数据创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 门锁操作记录表
CREATE TABLE IF NOT EXISTS locker_history
(
    room_id VARCHAR(4) NOT NULL COMMENT '房间门牌号',
    hotel_uuid VARCHAR(36) NOT NULL COMMENT '旅店数据在库中的主键',
    action TINYINT NOT NULL COMMENT '1:使用密码开门, 2:使用门把手开门, 3:门锁关闭',
    action_time DATETIME COMMENT '动作触发时间',
    create_time DATETIME COMMENT '数据创建时间'
)ENGINE=InnoDB DEFAULT CHARSET=utf8;