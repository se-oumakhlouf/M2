-- Drop tables if exist
DROP TABLE IF EXISTS sales CASCADE;
DROP TABLE IF EXISTS products CASCADE;
DROP TABLE IF EXISTS product_categories CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS cities CASCADE;
DROP TABLE IF EXISTS dates CASCADE;

-- Dimension: Product Categories
CREATE TABLE product_categories
(
    category_id   SERIAL PRIMARY KEY,
    category_name VARCHAR(50)
);

-- Dimension: Products
CREATE TABLE products
(
    product_id   SERIAL PRIMARY KEY,
    product_name VARCHAR(100),
    category_id  INT REFERENCES product_categories (category_id),
    price        NUMERIC(10, 2)
);

-- Dimension: Cities
CREATE TABLE cities
(
    city_id   SERIAL PRIMARY KEY,
    city_name VARCHAR(50),
    region    VARCHAR(50)
);

-- Dimension: Customers
CREATE TABLE customers
(
    customer_id   SERIAL PRIMARY KEY,
    customer_name VARCHAR(100),
    age           INT,
    city_id       INT REFERENCES cities (city_id)
);

-- Dimension: Dates
CREATE TABLE dates
(
    date_id DATE PRIMARY KEY,
    year    INT,
    month   INT,
    day     INT
);

-- Fact: Sales
CREATE TABLE sales
(
    sale_id     SERIAL PRIMARY KEY,
    product_id  INT REFERENCES products (product_id),
    customer_id INT REFERENCES customers (customer_id),
    date_id     DATE REFERENCES dates (date_id),
    quantity    INT,
    amount      NUMERIC(10, 2)
);