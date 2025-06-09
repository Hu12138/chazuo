-- 银行卡表
drop table if exists bank_card;
CREATE TABLE bank_card (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                           user_id BIGINT NOT NULL COMMENT '用户ID',
                           card_number VARCHAR(19) NOT NULL COMMENT '银行卡号',
                           bank_name VARCHAR(100) NOT NULL COMMENT '银行名称',
                           card_type VARCHAR(50) NOT NULL COMMENT '卡类型',
                           card_holder VARCHAR(100) NOT NULL COMMENT '持卡人姓名',
                           card_phone VARCHAR(11) NOT NULL COMMENT '预留手机号',
                           id_card VARCHAR(18) NOT NULL COMMENT '身份证号',
                           province VARCHAR(50) NOT NULL COMMENT '省份',
                           city VARCHAR(50) NOT NULL COMMENT '城市',

                           `status` tinyint DEFAULT 1 COMMENT '状态(0-已解绑,1-已绑定)',
                           `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户银行卡表';