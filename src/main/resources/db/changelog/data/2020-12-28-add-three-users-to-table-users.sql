-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]


-- changeset Valentin:Add_three_new_user_entities_to_users_table
INSERT INTO users (email, password, first_name, last_name, role, status)
VALUES ('admin@mail.com', '{noop}admin-password', 'Administrator','ADMINISTRATOR', 'ADMIN','ACTIVE'),
       ('user@mail.com', '{noop}user-password', 'User','U-S-E-R', 'USER','ACTIVE'),
       ('root@mail.com', '{noop}root-password', 'Root','ROOT', 'ADMIN','BANNED');
-- Real passwords be encrypted by Bcrypt with 12 passes https://bcrypt-generator.com/.