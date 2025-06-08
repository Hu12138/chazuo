-- 银行卡表
CREATE TABLE `bank_card` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `card_number` varchar(20) NOT NULL COMMENT '银行卡号',
  `bank_name` varchar(50) NOT NULL COMMENT '银行名称',
  `card_type` varchar(20) NOT NULL COMMENT '卡类型(储蓄卡/信用卡)',
  `card_holder` varchar(50) NOT NULL COMMENT '持卡人姓名',
  `phone` varchar(20) NOT NULL COMMENT '预留手机号',
  `is_default` tinyint(1) DEFAULT 0 COMMENT '是否默认卡(0-否,1-是)',
  `status` tinyint DEFAULT 1 COMMENT '状态(0-已解绑,1-已绑定)',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_id` (`user_id`),
  KEY `idx_card_number` (`card_number`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户银行卡表';
