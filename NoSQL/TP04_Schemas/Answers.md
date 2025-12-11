# Star Schema

- 1.2 :
    - a :
        ```sql
        select sum(amount) from sales;
        --- 2840
        ```

    - b : 
        ```sql
        select category, sum(amount) as revenue 
        from sales natural join products
        group by category;
        /*
            category   | revenue
            -------------+---------
            Furniture   |  195.00
            Electronics | 2475.00
            Sportswear  |  170.00
        */
        ```

    - c :
        ```sql
        select customer_id, customer_name, city, age, sum(amount) as revenu
        from sales natural join customers
        group by customer_id, customer_name, city, age
        order by revenu
        desc
        limit 1;
        /*
             customer_id | customer_name |   city   | age | revenu
            -------------+---------------+----------+-----+---------
                       1 | Alice Johnson | New York |  34 | 1250.00
        */
        ```

- 1.3 :
    - a :
        ```sql
        create materialized view sales_view as select * from sales;
        ```
    
    - b :
        ```sql
        INSERT INTO sales (product_id, customer_id, date_id, quantity, amount)
        VALUES (1, 1, '2025-01-01', 3, 3600.00); -- Alice buys 3 more laptops

        select * from sales_view; -- ne montre pas le nouvel achat de Alice

        -- il faut REFRESH la vue

        refresh materialized view sales_view;

        -- à présent le nouvel achat d'Alice est présent dans la vue

        ``` 