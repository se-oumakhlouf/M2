-- Drop tables if they exist
DROP TABLE IF EXISTS sales CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS dates CASCADE;

-- Dimension: Products
CREATE TABLE products
(
    product_id   SERIAL PRIMARY KEY,
    product_name VARCHAR(100),
    category     VARCHAR(50),
    price        NUMERIC(10, 2)
);

-- Dimension: Customers
CREATE TABLE customers
(
    customer_id   SERIAL PRIMARY KEY,
    customer_name VARCHAR(100),
    city          VARCHAR(50),
    age           INT
);

-- Dimension: Dates
CREATE TABLE dates
(
    date_id DATE PRIMARY KEY,
    year    INT,
    month   INT,
    day     INT
);

-- Fact table: Sales
CREATE TABLE sales
(
    sale_id     SERIAL PRIMARY KEY,
    product_id  INT REFERENCES products (product_id),
    customer_id INT REFERENCES customers (customer_id),
    date_id     DATE REFERENCES dates (date_id),
    quantity    INT,
    amount      NUMERIC(10, 2)
);