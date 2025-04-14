-- Categorías

INSERT INTO categories (name, parent_category_id) VALUES ( 'Bebidas', NULL);
INSERT INTO categories (name, parent_category_id) VALUES ( 'Snacks', NULL);
INSERT INTO categories (name, parent_category_id) VALUES ( 'Bebidas Alcohólicas', 1);
INSERT INTO categories (name, parent_category_id) VALUES ( 'Bebidas Sin Alcohol', 1);

-- Productos

INSERT INTO products (name, price, image_url, stock, category_id) VALUES
      ('Coca-Cola', 3.00, 'https://example.com/images/cocacola.png', 50, 4),
      ( 'Agua Mineral', 2.50, 'https://example.com/images/agua.png', 100, 4),
      ( 'Vino Tinto', 5.00, 'https://example.com/images/vino.png', 20, 3),
      ( 'Whisky', 7.00, 'https://example.com/images/whisky.png', 10, 3),
      ( 'Galletas Saladas', 1.50, 'https://example.com/images/galletas.png', 60, 2),
      ( 'Chips de Patata', 1.80, 'https://example.com/images/chips.png', 75, 2),
      ( 'Barrita Energética', 2.20, 'https://example.com/images/barrita.png', 40, 2);