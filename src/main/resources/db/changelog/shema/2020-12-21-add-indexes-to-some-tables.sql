-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]

-- changeset Valentin:Add_index_to_name_field_in_items_table
CREATE INDEX item_name_index
    ON items (name);

-- changeset Valentin:Add_index_to_name_field_in_storehouses_table
CREATE INDEX storehouse_name_index
    ON storehouses (name);

-- changeset Valentin:Add_index_to_name_field_in_categories_table
CREATE INDEX category_name_index
    ON categories (name);

-- changeset Valentin:Add_index_to_name_field_in_suppliers_table
CREATE INDEX supplier_name_index
    ON suppliers (name);

-- changeset Valentin:Add_uniqueness_constrained_index_sku-supplier
CREATE UNIQUE INDEX items_unique_supplier_sku_index
    ON items (supplier_id, sku);
