USE db_user;

CREATE TABLE IF NOT EXISTS `pe_user` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
    `username` varchar(64) NOT NULL DEFAULT '' COMMENT 'username',
    `password` varchar(64) NOT NULL DEFAULT '' COMMENT 'password',
    `nickname` varchar(64) NOT NULL DEFAULT '' COMMENT 'nickname',
    `mobile` varchar(64) NOT NULL DEFAULT '' COMMENT 'mobile',
    `email` varchar(64) NOT NULL DEFAULT '' COMMENT 'email',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='pe_user';

CREATE TABLE IF NOT EXISTS `pe_teacher` (
    `user_id` int(11) NOT NULL COMMENT 'user_id',
    `intro` varchar(64) NOT NULL DEFAULT '' COMMENT 'intro',
    `rate` int(5) NOT NULL DEFAULT 0 COMMENT 'rate',
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='pe_teacher';