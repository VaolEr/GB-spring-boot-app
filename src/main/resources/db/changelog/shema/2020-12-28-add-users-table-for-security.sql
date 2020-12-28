-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]

-- changeset Valentin:Create_users_table_for_store_users_data_used_in_spring_security
CREATE TABLE users
(
    -- SERIAL type "id" is BIGINT
    id          integer UNSIGNED NOT NULL AUTO_INCREMENT,
    email       VARCHAR(255)    UNIQUE          NOT NULL,
    password    VARCHAR(255)                    NOT NULL,
    first_name  VARCHAR(50)                     NOT NULL,
    last_name   VARCHAR(100)                    NOT NULL,
    role        VARCHAR(20) default 'USER'      NOT NULL,
    status      VARCHAR(20) default 'ACTIVE'    NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id)
);