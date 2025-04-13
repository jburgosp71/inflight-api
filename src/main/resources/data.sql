-- Categorías

INSERT INTO categories (id, name, parent_category_id) VALUES (1, 'Bebidas', NULL);
INSERT INTO categories (id, name, parent_category_id) VALUES (2, 'Snacks', NULL);
INSERT INTO categories (id, name, parent_category_id) VALUES (3, 'Bebidas Alcohólicas', 1);
INSERT INTO categories (id, name, parent_category_id) VALUES (4, 'Bebidas Sin Alcohol', 1);

-- Productos

INSERT INTO products (id, name, price, image_url, stock, category_id) VALUES
      (1, 'Coca-Cola', 3.00, 'https://example.com/images/cocacola.png', 50, 4),
      (2, 'Agua Mineral', 2.50, 'https://example.com/images/agua.png', 100, 4),
      (3, 'Vino Tinto', 5.00, 'https://example.com/images/vino.png', 20, 3),
      (4, 'Whisky', 7.00, 'https://example.com/images/whisky.png', 10, 3),
      (5, 'Galletas Saladas', 1.50, 'https://example.com/images/galletas.png', 60, 2),
      (6, 'Chips de Patata', 1.80, 'https://example.com/images/chips.png', 75, 2),
      (7, 'Barrita Energética', 2.20, 'https://example.com/images/barrita.png', 40, 2);