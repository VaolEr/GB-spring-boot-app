-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]

-- changeset Valentin:Add_three_new_user_entities_to_users_table
INSERT INTO users (email, password, first_name, last_name, role, status)
VALUES ('admin@mail.com', '$2y$12$BKw3hKmVk90gB3m8TMwGYOyJi3vXgN7vvZYae/SyI0PeKsEtHU6G2', 'Administrator','ADMINISTRATOR', 'ADMIN','ACTIVE'),
       ('user@mail.com', '$2y$12$OnC0Ej8AbMe8KRzDS0Hp7OpegxYCO2JOZP68FRdtNqEAGeslHWhWW', 'User','U-S-E-R', 'USER','ACTIVE'),
       ('root@mail.com', '$2y$12$gRiiWgqfxsMZdeuwDbu4WefiCsxzMl695vWJ4MYuyNQbXddDpC0Gu', 'Root','ROOT', 'ADMIN','BANNED');