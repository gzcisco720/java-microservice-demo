CREATE DATABASE IF NOT EXISTS `db_user`;
CREATE DATABASE IF NOT EXISTS `db_course`;

CREATE USER 'eric'@'%' IDENTIFIED BY 'admin';
GRANT ALL PRIVILEGES ON *.* TO 'eric'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;