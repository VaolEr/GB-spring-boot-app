-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]

-- changeset Valentin:Add_units_to_items
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'шт') WHERE (id = 1000);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'шт') WHERE (id = 1001);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1002);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1003);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1004);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1005);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1006);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'упа') WHERE (id = 1007);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'шт') WHERE (id = 1008);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'шт') WHERE (id = 1009);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'шт') WHERE (id = 1010);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1011);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1012);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1013);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1014);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1015);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1016);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1017);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1018);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1019);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1020);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1021);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1022);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'кг') WHERE (id = 1023);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'шт') WHERE (id = 1024);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'шт') WHERE (id = 1025);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'шт') WHERE (id = 1026);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'л') WHERE (id = 1027);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'л') WHERE (id = 1028);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'л') WHERE (id = 1029);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'л') WHERE (id = 1030);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'л') WHERE (id = 1031);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'л') WHERE (id = 1032);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'шт') WHERE (id = 1033);
UPDATE items SET unit_id = (SELECT id FROM units WHERE name = 'шт') WHERE (id = 1034);