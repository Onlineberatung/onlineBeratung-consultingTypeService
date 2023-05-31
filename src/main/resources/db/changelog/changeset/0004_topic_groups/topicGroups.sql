CREATE TABLE IF NOT EXISTS `consultingtypeservice`.`topic_group`
(
    `id`          bigint(21)                           NOT NULL,
    `name`        varchar(100) COLLATE utf8_unicode_ci NOT NULL,
    `create_date` datetime                             NOT NULL DEFAULT (UTC_TIMESTAMP),
    `update_date` datetime                             NOT NULL DEFAULT (UTC_TIMESTAMP),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;

CREATE SEQUENCE consultingtypeservice.sequence_topic_group
    INCREMENT BY 1
    MINVALUE = 0
    NOMAXVALUE
    START WITH 0
    CACHE 0;

CREATE TABLE IF NOT EXISTS `consultingtypeservice`.`topic_group_x_topic`
(
    `group_id`    bigint(21) NOT NULL,
    `topic_id`    bigint(21) NOT NULL,
    `create_date` datetime   NOT NULL DEFAULT (UTC_TIMESTAMP),
    `update_date` datetime   NOT NULL DEFAULT (UTC_TIMESTAMP),
    KEY `group_id` (`group_id`),
    CONSTRAINT `fk_group` FOREIGN KEY (`group_id`) REFERENCES `topic_group` (`id`) ON UPDATE CASCADE ON DELETE CASCADE,
    KEY `topic_id` (`topic_id`),
    CONSTRAINT `fk_topic` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;

CREATE SEQUENCE `consultingtypeservice`.`sequence_topic_group_x_topic`
    INCREMENT BY 1
    MINVALUE = 0
    NOMAXVALUE
    START WITH 0
    CACHE 10;