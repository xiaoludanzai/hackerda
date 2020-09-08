CREATE TABLE `wechat_openid`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `openid`       varchar(64) NOT NULL COMMENT 'openid',
    `account`      int(32)     NOT NULL COMMENT '学号',
    `is_bind`      tinyint(1)  NOT NULL DEFAULT '1' COMMENT '0为未绑定，1为已绑定',
    `appid`        varchar(32) NOT NULL COMMENT '来源信息',
    `gmt_create`   timestamp   NULL     DEFAULT CURRENT_TIMESTAMP COMMENT '创建日期',
    `gmt_modified` timestamp   NULL     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_openid` (`openid`, `appid`),
    KEY `idx_account` (`account`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `student`
(
    `id`           int(11)     NOT NULL AUTO_INCREMENT,
    `account`      int(32)     NOT NULL,
    `password`     varchar(64) NOT NULL,
    `name`         varchar(32) NOT NULL,
    `sex`          varchar(32) NOT NULL,
    `ethnic`       varchar(32) NOT NULL,
    `urpclass_num` int(11)     NOT NULL,
    `is_correct`   tinyint(1)  NOT NULL DEFAULT '1',
    `academy_name` varchar(255)         DEFAULT NULL,
    `subject_name` varchar(255)         DEFAULT NULL,
    `class_name`   varchar(255)         DEFAULT NULL,
    `gmt_create`   timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modified` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `pk_account` (`account`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `urp_class`
(
    `id`              int(11)      NOT NULL AUTO_INCREMENT,
    `admission_grade` varchar(255) NOT NULL COMMENT '年级',
    `class_num`       varchar(255) NOT NULL,
    `class_name`      varchar(255) NOT NULL,
    `academy_name`    varchar(255) NOT NULL,
    `academy_num`     varchar(255) NOT NULL,
    `subject_name`    varchar(255) NOT NULL,
    `subject_num`     varchar(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `class_num` (`class_num`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `user`
(
    `id`                   int(11)      NOT NULL AUTO_INCREMENT,
    `user_name`            varchar(64) NOT NULL,
    `nick_name`            varchar(64) NOT NULL,
    `password`             varchar(255) NOT NULL,
    `salt`                 varchar(255) NOT NULL,
    `avatar_path`          varchar(255) NOT NULL,
    `email`                varchar(255) NOT NULL DEFAULT '',
    `mobile`               varchar(32) NOT NULL,
    `gender`               int(16)     NOT NULL,
    `introduction`         varchar(255) NOT NULL,
    `user_type`            int(16)     NOT NULL,
    `use_default_password` tinyint(255) NOT NULL,
    `life_cycle_status`    int(16)      NOT NULL,
    `gmt_create`           timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `gmt_modify`           timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_name` (`user_name`),
    UNIQUE KEY `mobile` (`mobile`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `user_student`
(
    `id`              int(11)      NOT NULL AUTO_INCREMENT,
    `student_account` varchar(64) NOT NULL,
    `user_name`       varchar(64) NOT NULL,
    `gmt_create`      timestamp    NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modify`      timestamp    NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_student_user_name` (`user_name`),
    UNIQUE KEY `user_student_student_account` (`student_account`)

) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `schedule_task`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT,
    `openid`       varchar(64)           DEFAULT '0' COMMENT 'openid',
    `scene`        int(11)               DEFAULT '0' COMMENT '场景值',
    `task_count`   int(11)               DEFAULT '0' COMMENT '发送次数',
    `send_status`  tinyint(4)            DEFAULT '0' COMMENT '是否发送成功',
    `is_subscribe` tinyint(4)            DEFAULT '0' COMMENT '是否订阅',
    `appid`        varchar(50)           DEFAULT '0' COMMENT 'appid',
    `gmt_create`   timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    `gmt_modify`   timestamp(3) NULL     DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3) COMMENT '修改日期',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8;


CREATE TABLE `role`
(
    `id`   int(11)      NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL,
    `code` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `role_permission`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `role_id`       int(11) NOT NULL,
    `permission_id` int(11) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;

CREATE TABLE `permission`
(
    `id`              int(11)      NOT NULL AUTO_INCREMENT,
    `permission_name` varchar(255) NOT NULL,
    `permission_code` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;


CREATE TABLE `user_role`
(
    `id`         int(11)      NOT NULL AUTO_INCREMENT,
    `user_name`  varchar(255) NOT NULL,
    `role_code`  varchar(255) NOT NULL,
    `gmt_create` timestamp    NULL DEFAULT CURRENT_TIMESTAMP,
    `gmt_modify` timestamp    NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `user_role_name` (`user_name`, `role_code`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4;