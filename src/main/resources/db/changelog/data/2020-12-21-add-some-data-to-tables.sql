-- liquibase formatted sql
-- Format: --changeset author:id attribute1:value1 attribute2:value2 [...]

-- changeset Valentin:Add_new_categories_to_categories_table
INSERT INTO categories (name)
VALUES ('meat'),
       ('poultry'),
       ('sausages'),
       ('fish'),
       ('fruits'),
       ('vegetables'),
       ('bread'),
       ('alcohol'),
       ('preserves');

-- changeset Valentin:Add_new_suppliers_to_suppliers_table
INSERT INTO suppliers (name)
VALUES ('Zelenograd Meat'),
       ('Okskaya pticefabrika'),
       ('Vkusnyatkovo'),
       ('Brig fish house'),
       ('Marokko gardens'),
       ('BelAgrTorg'),
       ('Pekarnya Na Domu'),
       ('Grandpa`s Whiskey'),
       ('10 sotok ogorod');

-- changeset Valentin:Add_new_items_to_items_table
INSERT INTO items (name, sku, supplier_id, category_id)
VALUES ('Beef', '#beef_ZM', (SELECT id FROM suppliers WHERE name = 'Zelenograd Meat'),
        (SELECT id FROM categories WHERE name = 'meat')),
       ('Pork', '#pork_ZM', (SELECT id FROM suppliers WHERE name = 'Zelenograd Meat'),
        (SELECT id FROM categories WHERE name = 'meat')),
       ('Minced meat', '#minced_meat_ZM', (SELECT id FROM suppliers WHERE name = 'Zelenograd Meat'),
        (SELECT id FROM categories WHERE name = 'meat')),
       ('Chicken', 'OPF_chicken_01', (SELECT id FROM suppliers WHERE name = 'Okskaya pticefabrika'),
        (SELECT id FROM categories WHERE name = 'poultry')),
       ('Minced meat', 'OPF_minced_meat_03', (SELECT id FROM suppliers WHERE name = 'Okskaya pticefabrika'),
        (SELECT id FROM categories WHERE name = 'poultry')),
       ('10 Eggs pack', 'OPF_eggs_pack_10', (SELECT id FROM suppliers WHERE name = 'Okskaya pticefabrika'),
        (SELECT id FROM categories WHERE name = 'poultry')),
       ('Murzilka', '#VKT_murz_doktorskaya', (SELECT id FROM suppliers WHERE name = 'Vkusnyatkovo'),
        (SELECT id FROM categories WHERE name = 'sausages')),
       ('Doktorskaya', '#doktorskaya_ZM', (SELECT id FROM suppliers WHERE name = 'Zelenograd Meat'),
        (SELECT id FROM categories WHERE name = 'sausages')),
       ('Servelat', '#VKT_servelat', (SELECT id FROM suppliers WHERE name = 'Vkusnyatkovo'),
        (SELECT id FROM categories WHERE name = 'sausages')),
       ('Okun', '#BFH_okun', (SELECT id FROM suppliers WHERE name = 'Brig fish house'),
        (SELECT id FROM categories WHERE name = 'fish')),
       ('Akula', '#BFH_akula', (SELECT id FROM suppliers WHERE name = 'Brig fish house'),
        (SELECT id FROM categories WHERE name = 'fish')),
       ('Tuna', '#BFH_tuna', (SELECT id FROM suppliers WHERE name = 'Brig fish house'),
        (SELECT id FROM categories WHERE name = 'fish')),
       ('Orange', '#MGs_oranges', (SELECT id FROM suppliers WHERE name = 'Marokko gardens'),
        (SELECT id FROM categories WHERE name = 'fruits')),
       ('Apple', '#MGs_apples', (SELECT id FROM suppliers WHERE name = 'Marokko gardens'),
        (SELECT id FROM categories WHERE name = 'fruits')),
       ('Apple', '#BAT_apples', (SELECT id FROM suppliers WHERE name = 'BelAgrTorg'),
        (SELECT id FROM categories WHERE name = 'fruits')),
       ('Pineapple', '#BAT_pineapples', (SELECT id FROM suppliers WHERE name = 'BelAgrTorg'),
        (SELECT id FROM categories WHERE name = 'fruits')),
       ('Banana', '#MGs_bananas', (SELECT id FROM suppliers WHERE name = 'Marokko gardens'),
        (SELECT id FROM categories WHERE name = 'fruits')),
       ('Cherry', '#BAT_cherrys', (SELECT id FROM suppliers WHERE name = 'BelAgrTorg'),
        (SELECT id FROM categories WHERE name = 'fruits')),
       ('Potato', '#BAT_potatos', (SELECT id FROM suppliers WHERE name = 'BelAgrTorg'),
        (SELECT id FROM categories WHERE name = 'vegetables')),
       ('Cabbage', '#BAT_сabbages', (SELECT id FROM suppliers WHERE name = 'BelAgrTorg'),
        (SELECT id FROM categories WHERE name = 'vegetables')),
       ('Tomato', '#BAT_tomatos', (SELECT id FROM suppliers WHERE name = 'BelAgrTorg'),
        (SELECT id FROM categories WHERE name = 'vegetables')),
       ('Tomato', '#MGs_tomatos', (SELECT id FROM suppliers WHERE name = 'Marokko gardens'),
        (SELECT id FROM categories WHERE name = 'vegetables')),
       ('Baton', '#PND_batons', (SELECT id FROM suppliers WHERE name = 'Pekarnya Na Domu'),
        (SELECT id FROM categories WHERE name = 'bread')),
       ('Baguette', '#PND_baguettes', (SELECT id FROM suppliers WHERE name = 'Pekarnya Na Domu'),
        (SELECT id FROM categories WHERE name = 'bread')),
       ('Bun', '#PND_buns', (SELECT id FROM suppliers WHERE name = 'Pekarnya Na Domu'),
        (SELECT id FROM categories WHERE name = 'bread')),
       ('Samogon-40', '#GsW_s40', (SELECT id FROM suppliers WHERE name = 'Grandpa`s Whiskey'),
        (SELECT id FROM categories WHERE name = 'alcohol')),
       ('Samogon-50', '#GsW_s50', (SELECT id FROM suppliers WHERE name = 'Grandpa`s Whiskey'),
        (SELECT id FROM categories WHERE name = 'alcohol')),
       ('Samogon-60', '#GsW_s60', (SELECT id FROM suppliers WHERE name = 'Grandpa`s Whiskey'),
        (SELECT id FROM categories WHERE name = 'alcohol')),
       ('Red Wine', '#GsW_red_wines', (SELECT id FROM suppliers WHERE name = 'Grandpa`s Whiskey'),
        (SELECT id FROM categories WHERE name = 'alcohol')),
       ('Apple Cider', '#BAT_apple_ciders', (SELECT id FROM suppliers WHERE name = 'BelAgrTorg'),
        (SELECT id FROM categories WHERE name = 'alcohol')),
       ('Pear Cider', '#BAT_pear_ciders', (SELECT id FROM suppliers WHERE name = 'BelAgrTorg'),
        (SELECT id FROM categories WHERE name = 'alcohol')),
       ('Pickled cucumbers', '#10SO_pickled_cucumbers', (SELECT id FROM suppliers WHERE name = '10 sotok ogorod'),
        (SELECT id FROM categories WHERE name = 'preserves')),
       ('Canned stew', '#10SO_canned_stews', (SELECT id FROM suppliers WHERE name = '10 sotok ogorod'),
        (SELECT id FROM categories WHERE name = 'preserves'));



-- changeset Valentin:Populate_items_storehouses_table
INSERT INTO items_storehouses (item_id, storehouse_id, qty)
VALUES ((SELECT id FROM items WHERE name = 'Beef'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 2),
       ((SELECT id FROM items WHERE name = 'Beef'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 7),
       ((SELECT id FROM items WHERE name = 'Pork'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 6),
       ((SELECT id FROM items WHERE name = 'Pork'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 3),
       ((SELECT id FROM items WHERE name = 'Minced meat' AND sku = '#minced_meat_ZM'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 6),
       ((SELECT id FROM items WHERE name = 'Chicken'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 7),
       ((SELECT id FROM items WHERE name = 'Minced meat' AND sku = 'OPF_minced_meat_03'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 36),
       ((SELECT id FROM items WHERE name = 'Minced meat' AND sku = 'OPF_minced_meat_03'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 52),
       ((SELECT id FROM items WHERE name = '10 Eggs pack'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 16),
       ((SELECT id FROM items WHERE name = 'Murzilka' AND sku = '#VKT_murz_doktorskaya'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 33),
       ((SELECT id FROM items WHERE name = 'Doktorskaya'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 3),
       ((SELECT id FROM items WHERE name = 'Servelat'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 369),
       ((SELECT id FROM items WHERE name = 'Servelat'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 16),
       ((SELECT id FROM items WHERE name = 'Okun'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 13),
       ((SELECT id FROM items WHERE name = 'Okun'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 89),
       ((SELECT id FROM items WHERE name = 'Akula'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 39),
       ((SELECT id FROM items WHERE name = 'Akula'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 18),
       ((SELECT id FROM items WHERE name = 'Tuna'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 7),
       ((SELECT id FROM items WHERE name = 'Tuna'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 7),
       ((SELECT id FROM items WHERE name = 'Orange'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 50),
       ((SELECT id FROM items WHERE name = 'Orange'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 1985),
       ((SELECT id FROM items WHERE name = 'Apple' AND sku = '#MGs_apples'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 859),
       ((SELECT id FROM items WHERE name = 'Apple' AND sku = '#MGs_apples'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 3589),
       ((SELECT id FROM items WHERE name = 'Apple' AND sku = '#BAT_apples'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 7698),
       ((SELECT id FROM items WHERE name = 'Apple' AND sku = '#BAT_apples'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 556),
       ((SELECT id FROM items WHERE name = 'Pineapple'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 22),
       ((SELECT id FROM items WHERE name = 'Banana'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 325),
       ((SELECT id FROM items WHERE name = 'Cherry'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 321),
       ((SELECT id FROM items WHERE name = 'Potato'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 30457),
       ((SELECT id FROM items WHERE name = 'Cabbage'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 327),
       ((SELECT id FROM items WHERE name = 'Tomato' AND sku = '#BAT_tomatos'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 38),
       ((SELECT id FROM items WHERE name = 'Tomato' AND sku = '#BAT_tomatos'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 85),
       ((SELECT id FROM items WHERE name = 'Tomato' AND sku = '#MGs_tomatos'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 88),
       ((SELECT id FROM items WHERE name = 'Baton'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 110),
       ((SELECT id FROM items WHERE name = 'Baton'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 75),
       ((SELECT id FROM items WHERE name = 'Baguette'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 28),
       ((SELECT id FROM items WHERE name = 'Bun'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 789),
       ((SELECT id FROM items WHERE name = 'Samogon-40'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 12),
       ((SELECT id FROM items WHERE name = 'Samogon-50'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 78),
       ((SELECT id FROM items WHERE name = 'Samogon-60'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 256),
       ((SELECT id FROM items WHERE name = 'Red Wine'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 777),
       ((SELECT id FROM items WHERE name = 'Apple Cider'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 222),
       ((SELECT id FROM items WHERE name = 'Apple Cider'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 333),
       ((SELECT id FROM items WHERE name = 'Pear Cider'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 222),
       ((SELECT id FROM items WHERE name = 'Pear Cider'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 333),
       ((SELECT id FROM items WHERE name = 'Pickled cucumbers'),
        (SELECT id FROM storehouses WHERE name = 'Nearest storehouse'), 569),
       ((SELECT id FROM items WHERE name = 'Canned stew'),
        (SELECT id FROM storehouses WHERE name = 'Distant warehouse'), 756);