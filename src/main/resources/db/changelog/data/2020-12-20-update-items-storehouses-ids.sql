-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]

-- changeset Dmitry:Update_PK_values_in_items_storehouses_table
UPDATE `items_storehouses` SET `id` = `id` + 1000;
