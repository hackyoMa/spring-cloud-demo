--liquibase formatted sql
--changeset hackyo:1.0.0
CREATE TABLE `permission`
(
    `id`          varchar(36)  NOT NULL,
    `parent_id`   varchar(36)  NOT NULL,
    `name`        varchar(100) NOT NULL,
    `service`     varchar(50)  NOT NULL,
    `url`         varchar(300) NOT NULL,
    `basics`      tinyint(1) NOT NULL,
    `description` varchar(500) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `role`
(
    `id`          varchar(36)  NOT NULL,
    `name`        varchar(100) NOT NULL,
    `basics`      tinyint(1) NOT NULL,
    `description` varchar(500) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `role_permission`
(
    `id`            varchar(36) NOT NULL,
    `role_id`       varchar(36) NOT NULL,
    `permission_id` varchar(36) NOT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `user`
(
    `id`                       varchar(36)  NOT NULL,
    `username`                 varchar(64)  NOT NULL,
    `password`                 varchar(128) NOT NULL,
    `name`                     varchar(20)  NOT NULL,
    `email`                    varchar(128) DEFAULT NULL,
    `phone`                    varchar(50)  DEFAULT NULL,
    `basics`                   tinyint(1) NOT NULL,
    `state`                    int(1) NOT NULL,
    `last_password_reset_time` datetime     NOT NULL,
    PRIMARY KEY (`id`)
);
CREATE TABLE `user_role`
(
    `id`      varchar(36) NOT NULL,
    `user_id` varchar(36) NOT NULL,
    `role_id` varchar(36) NOT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO `user` (`id`, `username`, `password`, `name`, `email`, `phone`, `basics`, `state`,
                    `last_password_reset_time`)
VALUES ('e5e4d892-63fa-278e-f3ef-5c3496e7f621', 'admin', '2fZCpVnqho7dWuAnR0tW5zUEZVNkHnwHzZHeFaOTtyU=', 'admin', NULL,
        NULL, 1, 0, '2022-01-19 14:31:40');
INSERT INTO `role` (`id`, `name`, `basics`, `description`)
VALUES ('a9a7b95d-6e62-4b71-a40f-de3887cdfb4a', 'admin', 1, 'have all permissions');
INSERT INTO `user_role` (`id`, `user_id`, `role_id`)
VALUES ('5ba129d2-ed37-8fe1-bf5e-948bb7537ecf', 'e5e4d892-63fa-278e-f3ef-5c3496e7f621',
        'a9a7b95d-6e62-4b71-a40f-de3887cdfb4a');
INSERT INTO `permission` (`id`, `parent_id`, `name`, `service`, `url`, `basics`, `description`)
VALUES ('348c9c20-4b74-347f-3343-d028b28df65b', 'root', 'user', 'user-service', '/user', 1, 'user');
INSERT INTO `permission` (`id`, `parent_id`, `name`, `service`, `url`, `basics`, `description`)
VALUES ('e4c62c10-ea07-c286-7556-e62ef4a355ac', '348c9c20-4b74-347f-3343-d028b28df65b', 'user-all-info',
        'user-service', '/user/all_info', 1, 'query user all info');
