-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]


-- changeset Valentin:Add_three_new_user_entities_to_users_table
INSERT INTO users (email, password, first_name, last_name, role, status)
VALUES ('admin@mail.com', '$2y$12$MQ.d8visyZi2A59VNU7gBO72GDXnLyVMq902Psg0rPWGJDSwKEeY2', 'Administrator','ADMINISTRATOR', 'ADMIN','ACTIVE'),
       ('user@mail.com', '$2y$12$ickypEafJjeVNeBg5RMpOu66i.o.3HFJZctBaXyWd51lT0u.IKdqq', 'User','U-S-E-R', 'USER','ACTIVE'),
       ('root@mail.com', '$2y$12$COIXCfJGM82CzNWtuWLIFu9gSvu8iiLd9pUynJVVevYedHwzzicWC', 'Root','ROOT', 'ADMIN','BANNED');
-- Password encrypted by Bcrypt with 12 passes https://bcrypt-generator.com/.