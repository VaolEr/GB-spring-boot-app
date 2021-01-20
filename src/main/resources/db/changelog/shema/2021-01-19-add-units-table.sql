-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]

-- changeset Valentin:Create_units_table
CREATE TABLE units
(
    -- SERIAL type "id" is BIGINT
    id   integer UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255)     NOT NULL,
    CONSTRAINT units_pk PRIMARY KEY (id)
);
ALTER TABLE units
    AUTO_INCREMENT = 1000;

-- changeset Valentin:Add_index_to_name_field_in_units_table
CREATE INDEX unit_name_index
    ON units (name);

-- changeset Valentin:Add units_id field to items
ALTER TABLE items
    ADD unit_id integer UNSIGNED default 1000 NOT NULL;