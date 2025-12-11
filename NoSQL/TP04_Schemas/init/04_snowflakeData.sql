-- Product Categories
INSERT INTO product_categories (category_name)
VALUES ('Electronics'),
       ('Furniture'),
       ('Sportswear');

-- Products
INSERT INTO products (product_name, category_id, price)
VALUES ('Laptop Pro 15"', 1, 1200.00),
       ('Wireless Mouse', 1, 25.00),
       ('Office Chair', 2, 150.00),
       ('Desk Lamp', 2, 45.00),
       ('Running Shoes', 3, 85.00);

-- Cities
INSERT INTO cities (city_name, region)
VALUES ('New York', 'East Coast'),
       ('Chicago', 'Midwest'),
       ('San Francisco', 'West Coast'),
       ('Boston', 'East Coast');

-- Customers
INSERT INTO customers (customer_name, age, city_id)
VALUES ('Alice Johnson', 34, 1),
       ('Bob Smith', 41, 2),
       ('Carla Gomez', 29, 3),
       ('David Lee', 37, 4);

-- Dates
INSERT INTO dates (date_id, year, month, day)
VALUES ('2025-01-01', 2025, 1, 1),
       ('2025-01-02', 2025, 1, 2),
       ('2025-01-03', 2025, 1, 3),
       ('2025-01-04', 2025, 1, 4);

-- Sales
INSERT INTO sales (product_id, customer_id, date_id, quantity, amount)
VALUES (1, 1, '2025-01-01', 1, 1200.00),
       (2, 1, '2025-01-01', 2, 50.00),
       (3, 2, '2025-01-02', 1, 150.00),
       (4, 2, '2025-01-02', 1, 45.00),
       (5, 3, '2025-01-03', 2, 170.00),
       (1, 4, '2025-01-04', 1, 1200.00),
       (2, 4, '2025-01-04', 1, 25.00);