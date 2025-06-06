-- chazuo.`user` definition

CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                        `openid` varchar(64) DEFAULT NULL COMMENT '微信openid',
                        `unionid` varchar(64) DEFAULT NULL COMMENT '微信unionid',
                        `session_key` varchar(128) DEFAULT NULL COMMENT '微信sessionKey',
                        `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
                        `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
                        `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `openid` (`openid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';