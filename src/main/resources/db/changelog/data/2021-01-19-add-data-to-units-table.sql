-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]


-- changeset Valentin:Add_units
INSERT INTO units (name)
VALUES ('шт'),
       ('кг'),
       ('г'),
       ('л'),
       ('пар'),
       ('упа');