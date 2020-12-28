-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]

-- changeset Valentin:Alter_items_storehouses_table_add_id_PK
ALTER TABLE items_storehouses
    ADD id integer UNSIGNED NOT NULL AUTO_INCREMENT,
    ADD CONSTRAINT items_storehouses_pk PRIMARY KEY (id);
ALTER TABLE items_storehouses
    AUTO_INCREMENT = 1000;
