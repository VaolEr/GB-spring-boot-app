-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]

-- changeset Dmitry:Populate_categories_table
INSERT INTO categories (name)
VALUES ('stationery'),
       ('magazines');

-- changeset Dmitry:Populate_storehouses_table
INSERT INTO storehouses (name)
VALUES ('Nearest storehouse'),
       ('Distant warehouse');

-- changeset Dmitry:Populate_suppliers_table
INSERT INTO suppliers (name)
VALUES ('Peter Pen'),
       ('Pencil');

-- changeset Dmitry:Populate_items_table
INSERT INTO items (name, sku, supplier_id, category_id)
VALUES ('Pen', '123', (SELECT id FROM suppliers WHERE name = 'Peter Pen'),
        (SELECT id FROM categories WHERE name = 'stationery')),
       ('Murzilka', '#mag321', (SELECT id FROM suppliers WHERE name = 'Pencil'),
        (SELECT id FROM categories WHERE name = 'magazines'));

-- changeset Dmitry:Populate_items_storehouses_table
INSERT INTO items_storehouses (item_id, storehouse_id, qty)
VALUES ((SELECT id FROM items WHERE name = 'Pen'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 5),
       ((SELECT id FROM items WHERE name = 'Murzilka'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 9);
