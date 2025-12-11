-- Products
INSERT INTO products (product_name, category, price)
VALUES ('Laptop Pro 15"', 'Electronics', 1200.00),
       ('Wireless Mouse', 'Electronics', 25.00),
       ('Office Chair', 'Furniture', 150.00),
       ('Desk Lamp', 'Furniture', 45.00),
       ('Running Shoes', 'Sportswear', 85.00);

-- Customers
INSERT INTO customers (customer_name, city, age)
VALUES ('Alice Johnson', 'New York', 34),
       ('Bob Smith', 'Chicago', 41),
       ('Carla Gomez', 'San Francisco', 29),
       ('David Lee', 'Boston', 37);

-- Dates
INSERT INTO dates (date_id, year, month, day)
VALUES ('2025-01-01', 2025, 1, 1),
       ('2025-01-02', 2025, 1, 2),
       ('2025-01-03', 2025, 1, 3),
       ('2025-01-04', 2025, 1, 4);

-- Sales
INSERT INTO sales (product_id, customer_id, date_id, quantity, amount)
VALUES (1, 1, '2025-01-01', 1, 1200.00), -- Alice buys a laptop
       (2, 1, '2025-01-01', 2, 50.00),   -- Alice buys 2 mice
       (3, 2, '2025-01-02', 1, 150.00),  -- Bob buys chair
       (4, 2, '2025-01-02', 1, 45.00),   -- Bob buys lamp
       (5, 3, '2025-01-03', 2, 170.00),  -- Carla buys shoes
       (1, 4, '2025-01-04', 1, 1200.00), -- David buys laptop
       (2, 4, '2025-01-04', 1, 25.00); -- David buys 1 mouse