CREATE TABLE consultingtypeservice.`topic` (
  `id` bigint(21) NOT NULL,
  `tenant_id` bigint(21) NULL,
  `name` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(100) NULL,
  `status` varchar(20) COLLATE utf8_unicode_ci,
  `create_date` datetime NOT NULL DEFAULT (UTC_TIMESTAMP),
  `update_date` datetime NOT NULL DEFAULT (UTC_TIMESTAMP),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

ALTER TABLE consultingtypeservice.`topic`
ADD CONSTRAINT unique_name UNIQUE (name, tenant_id);

CREATE SEQUENCE consultingtypeservice.sequence_topic
INCREMENT BY 1
MINVALUE = 0
NOMAXVALUE
START WITH 0
CACHE 0;
