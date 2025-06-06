-- chazuo.`role` definition

CREATE TABLE `role` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                        `name` varchar(50) NOT NULL COMMENT '角色名称',
                        `code` varchar(50) NOT NULL COMMENT '角色编码',
                        `description` varchar(200) DEFAULT NULL COMMENT '角色描述',
                        `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                        `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

-- chazuo.user_role definition

CREATE TABLE `user_role` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                             `user_id` bigint NOT NULL COMMENT '用户ID',
                             `role_id` bigint NOT NULL COMMENT '角色ID',
                             `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `uk_user_role` (`user_id`,`role_id`),
                             KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关联表';