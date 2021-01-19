-- changeset Valentin:Add units_id field to items and foreign_key2
ALTER TABLE items
    ADD unit_id integer UNSIGNED default 1000 NOT NULL,
    ADD CONSTRAINT items_fk2 FOREIGN KEY (unit_id) REFERENCES units (id);