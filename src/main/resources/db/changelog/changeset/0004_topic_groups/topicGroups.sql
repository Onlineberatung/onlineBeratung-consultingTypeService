/* group */

CREATE TABLE IF NOT EXISTS `consultingtypeservice`.`group`
(
    `id`          bigint(21)                           NOT NULL,
    `name`        varchar(100) COLLATE utf8_unicode_ci NOT NULL,
    `create_date` datetime                             NOT NULL DEFAULT (UTC_TIMESTAMP),
    `update_date` datetime                             NOT NULL DEFAULT (UTC_TIMESTAMP),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;

CREATE SEQUENCE consultingtypeservice.sequence_group
    INCREMENT BY 1
    MINVALUE = 0
    NOMAXVALUE
    START WITH 0
    CACHE 0;

/* group_topic */

CREATE TABLE IF NOT EXISTS `consultingtypeservice`.`group_topic`
(
    `id`          bigint(21) NOT NULL,
    `group_id`    bigint(21) NOT NULL,
    `topic_id`    bigint(21) NOT NULL,
    `create_date` datetime   NOT NULL DEFAULT (UTC_TIMESTAMP),
    `update_date` datetime   NOT NULL DEFAULT (UTC_TIMESTAMP),
    PRIMARY KEY (`id`),
    KEY `group_id` (`group_id`),
    CONSTRAINT `fk_group` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`) ON UPDATE CASCADE,
    KEY `topic_id` (`topic_id`),
    CONSTRAINT `fk_topic` FOREIGN KEY (`topic_id`) REFERENCES `topic` (`id`) ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_unicode_ci;

CREATE SEQUENCE `consultingtypeservice`.`sequence_group_topic`
    INCREMENT BY 1
    MINVALUE = 0
    NOMAXVALUE
    START WITH 0
    CACHE 10;