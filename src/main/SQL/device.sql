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

    -- 通用字段
                                    `is_active` tinyint(1) DEFAULT '1' COMMENT '是否启用',
                                    `created_by` bigint NOT NULL COMMENT '创建人ID',
                                    `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',

    -- 按电量收费专用字段
                                    `has_service_fee` tinyint(1) DEFAULT '0' COMMENT '是否开启服务费(按电量)',
                                    `service_fee_per_unit` decimal(10,4) DEFAULT NULL COMMENT '服务费单价(元/度)(按电量)',
                                    `energy_fee_per_unit` decimal(10,4) DEFAULT NULL COMMENT '电费单价(元/度)(按电量)',

    -- 按充电时长收费专用字段
                                    `time_unit` enum('MINUTE','HOUR') DEFAULT NULL COMMENT '时间单位(分钟/小时)(按时长)',
                                    `time_per_yuan` int DEFAULT NULL COMMENT '每元充多少时间(按时长)',
                                    `time_unit_per_yuan` enum('MINUTE','HOUR') DEFAULT NULL COMMENT '时间单位(与time_per_yuan配合)(按时长)',

    -- 按金额充电专用字段
                                    `amount_time_unit` enum('MINUTE','HOUR') DEFAULT NULL COMMENT '时间单位(分钟/小时)(按金额)',
                                    `amount_per_unit` decimal(10,4) DEFAULT NULL COMMENT '每单位时间价格(元)(按金额)',

                                    PRIMARY KEY (`id`),
                                    KEY `idx_type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='合并后的收费标准表';
drop table if exists `pricing_by_energy`;

drop table if exists `pricing_by_time`;


drop table if exists `pricing_by_amount`;
