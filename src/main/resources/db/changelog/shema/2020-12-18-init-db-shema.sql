-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]

-- changeset Dmitry:Create_items_table
CREATE TABLE items
(
    -- SERIAL type "id" is BIGINT
    id          integer UNSIGNED NOT NULL AUTO_INCREMENT,
    name        VARCHAR(255)     NOT NULL,
    sku         VARCHAR(255)     NOT NULL,
    supplier_id integer UNSIGNED NOT NULL,
    category_id integer UNSIGNED NOT NULL,
    CONSTRAINT items_pk PRIMARY KEY (id)
);
-- ) AUTO_INCREMENT=1000;

-- changeset Dmitry:Create_categories_table
CREATE TABLE categories
(
    id   integer UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255)     NOT NULL,

    CONSTRAINT categories_pk PRIMARY KEY (id)
);

-- changeset Dmitry:Create_storehouses_table
CREATE TABLE storehouses
(
    id   integer UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255)     NOT NULL,
    CONSTRAINT storehouses_pk PRIMARY KEY (id)
);

-- changeset Dmitry:Create_suppliers_table
CREATE TABLE suppliers
(
    id   integer UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255)     NOT NULL,
    CONSTRAINT suppliers_pk PRIMARY KEY (id)
);

-- changeset Dmitry:Create_items_storehouses_table
CREATE TABLE items_storehouses
(
    item_id       integer UNSIGNED NOT NULL,
    storehouse_id integer UNSIGNED NOT NULL,
    qty           integer          NOT NULL
);

-- changeset Dmitry:Alter_items_table_fks
ALTER TABLE items
    ADD CONSTRAINT items_fk0 FOREIGN KEY (supplier_id) REFERENCES suppliers (id);
ALTER TABLE items
    ADD CONSTRAINT items_fk1 FOREIGN KEY (category_id) REFERENCES categories (id);

-- changeset Dmitry:Alter_items_storehouses_table_fks
ALTER TABLE items_storehouses
    ADD CONSTRAINT items_storehouses_fk0 FOREIGN KEY (item_id) REFERENCES items (id);
ALTER TABLE items_storehouses
    ADD CONSTRAINT items_storehouses_fk1 FOREIGN KEY (storehouse_id) REFERENCES storehouses (id);

-- changeset Dmitry:Alter_tables_autoincrement_start_value
ALTER TABLE items
    AUTO_INCREMENT = 1000;
ALTER TABLE categories
    AUTO_INCREMENT = 1000;
ALTER TABLE storehouses
    AUTO_INCREMENT = 1000;
ALTER TABLE suppliers
    AUTO_INCREMENT = 1000;