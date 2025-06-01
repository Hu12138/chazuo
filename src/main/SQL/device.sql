drop table if exists `device`;
CREATE TABLE `device` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                          `device_no` varchar(50) NOT NULL COMMENT '设备编号',
                          `device_name` varchar(100) NOT NULL COMMENT '设备名称',
                          `pricing_standard_id` bigint NOT NULL COMMENT '收费标准ID（逻辑关联）',
                          `status` tinyint DEFAULT '1' COMMENT '设备状态(0-禁用,1-启用)',
                          `created_by` bigint NOT NULL COMMENT '创建人ID',
                          `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                          `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                          PRIMARY KEY (`id`),
                          UNIQUE KEY `uk_device_no` (`device_no`),
                          KEY `idx_pricing_standard_id` (`pricing_standard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备表';
drop table if exists `pricing_standard`;
CREATE TABLE `pricing_standard` (
                                    `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                    `name` varchar(100) NOT NULL COMMENT '收费标准名称',
                                    `type` enum('BY_ENERGY','BY_TIME','BY_AMOUNT') NOT NULL COMMENT '收费类型(按电量/按时长/按金额)',
                                    `is_active` tinyint(1) DEFAULT '1' COMMENT '是否启用',
                                    `created_by` bigint NOT NULL COMMENT '创建人ID',
                                    `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                    PRIMARY KEY (`id`),
                                    KEY `idx_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='收费标准基础表';

drop table if exists `pricing_by_energy`;
CREATE TABLE `pricing_by_energy` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                     `standard_id` bigint NOT NULL COMMENT '关联收费标准ID（逻辑关联）',
                                     `has_service_fee` tinyint(1) DEFAULT '0' COMMENT '是否开启服务费',
                                     `service_fee_per_unit` decimal(10,4) DEFAULT '0.0000' COMMENT '服务费单价(元/度)',
                                     `energy_fee_per_unit` decimal(10,4) NOT NULL COMMENT '电费单价(元/度)',
                                     `created_by` bigint NOT NULL COMMENT '创建人ID',
                                     `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `uk_standard_id` (`standard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='按电量收费标准表';

drop table if exists `pricing_by_time`;
CREATE TABLE `pricing_by_time` (
                                   `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                   `standard_id` bigint NOT NULL COMMENT '关联收费标准ID（逻辑关联）',
                                   `time_unit` enum('minute','hour') NOT NULL COMMENT '时间单位(分钟/小时)',
                                   `time_per_yuan` int NOT NULL COMMENT '每元充多少时间',
                                   `time_unit_per_yuan` enum('minute','hour') NOT NULL COMMENT '时间单位(与time_per_yuan配合)',
                                   `created_by` bigint NOT NULL COMMENT '创建人ID',
                                   `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                   `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                   PRIMARY KEY (`id`),
                                   UNIQUE KEY `uk_standard_id` (`standard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='按充电时长收费标准表';

drop table if exists `pricing_by_amount`;
CREATE TABLE `pricing_by_amount` (
                                     `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                                     `standard_id` bigint NOT NULL COMMENT '关联收费标准ID（逻辑关联）',
                                     `time_unit` enum('minute','hour') NOT NULL COMMENT '时间单位(分钟/小时)',
                                     `amount_per_unit` decimal(10,4) NOT NULL COMMENT '每单位时间价格(元)',
                                     `created_by` bigint NOT NULL COMMENT '创建人ID',
                                     `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     PRIMARY KEY (`id`),
                                     UNIQUE KEY `uk_standard_id` (`standard_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='按金额充电标准表';