drop table IF EXISTS `topic`;

CREATE TABLE IF NOT EXISTS `topic`
  (
     `id`                  BIGINT NOT NULL,
     `tenant_id`           BIGINT NULL,
     `name`                VARCHAR (100) NOT NULL,
     `description`         VARCHAR (100) NULL,
     `status`              VARCHAR (20),
     `create_date`         TIMESTAMP NOT NULL,
     `update_date`         TIMESTAMP NULL,
     `internal_identifier` VARCHAR (50) NULL,
     PRIMARY KEY (`id`)
  );

alter table `topic`
    ADD CONSTRAINT IF NOT EXISTS unique_name_per_tenant UNIQUE (name, tenant_id);

create sequence IF NOT EXISTS sequence_topic
INCREMENT BY 1
START with 100000;

insert into TOPIC (`id`, `tenant_id`, `name`, `description`, `status`, `create_date`)
VALUES (1, '1', '{"de" : "de an active topic", "en": "en an active topic"}',
        '{"de" : "de description", "en": "en description"}', 'ACTIVE',
        '2022-06-02');

insert into TOPIC (`id`, `tenant_id`, `name`, `description`, `status`, `create_date`)
VALUES (2, '1', '{"de" : "de not an active topic", "en": "en not an active topic"}',
        '{"de" : "de description", "en": "en description"}', 'INACTIVE', '2022-06-02');

insert into TOPIC (`id`, `tenant_id`, `name`, `description`, `status`, `create_date`)
VALUES (3, '2', '{"de" : "de another topic"}', '{"de" : "de description"}', 'ACTIVE', '2022-06-02');
