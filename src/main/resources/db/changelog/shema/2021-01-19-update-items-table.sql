-- changeset Valentin:Add foreign_key2 to unit_id in items table
ALTER TABLE items
    ADD CONSTRAINT items_fk2 FOREIGN KEY (unit_id) REFERENCES units (id);