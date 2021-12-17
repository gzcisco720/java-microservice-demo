USE db_course;

CREATE TABLE IF NOT EXISTS `pe_course` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'primary key',
    `title` varchar(64) NOT NULL DEFAULT '' COMMENT 'username',
    `description` varchar(128) NOT NULL DEFAULT '' COMMENT 'password',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='pe_course';

CREATE TABLE IF NOT EXISTS `pr_user_course` (
    `user_id` int(11) NOT NULL COMMENT 'user_id',
    `course_id` int(11) NOT NULL COMMENT 'course_id',
    PRIMARY KEY (`user_id`,`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='pr_user_course';