ALTER TABLE `asset_manage`.`asset` 
MODIFY COLUMN `project_name` varchar(210) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称' AFTER `id`,
MODIFY COLUMN `custom_name` varchar(210) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称' AFTER `project_name`;