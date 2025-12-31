# NoSQL & Graph Databases

</br>

- ## Theoretical Foundations *(Labdeled Graphs & RPQs)*

    - **Data Model :** Labeled Graphs
        - **Vertices *(V)* :** Finite set of nodes
        - **Labels *(L)* :** Finite set of symbols *(alphabet)*
        - **Edges *(E)* :** Directed connections V x L x V *(single label per edge)*

    - **Regular Path Queries *(RPQs)* :**
        - **Definition :** A regular expression *(RegExp)* over the alphabet *L* sent to a graph to match walks
        - **Syntax :**
            - **Atom :** `A` *(matches an edge labeled A)*
            - **Concatenation :** `A.B` *(A followed by B)*
            - **Disjunction :** `A+B` *(A or B)*
            - **Kleene Start :** `A*` *(0 or more repetitions of A)*
        - **Semantics :**
            - **Endpoint Semantics :**
                - Returns pair of nodes *(source, target)*
                - **Used by :** SPARQL (RDF), GQL (ANY WALK)
                - **Pros :** Efficient, small output
                - **Cons :** Loss of path information
            - **Shortest Semantics :**
                - Returns the shortes walk(s) between endpoints
                - **Used by :** GSQL (TigerGraph), PGQL (Oracle)
                - **Pros :** Efficient (BFS), specific answer
                - **Cons :** *"Shortest"* is not always *"Best"*
            - **Trail Semantics :**
                - Returns all walks without repeated edges
                - **Used by :** **Cypher** (Neo4j), GQL (ALL TRAIL)
                - **Pros :** Meaningful paths
                - **Cons :** Computationally expensive (NP-Hard in worst cases)
        - **Extensions :**
            - **2RPQs :** Allows backward navigation *(inverse edges)*
            - **CRPQs *(Conjunctive)* :** Pattern matching where edges are RPQs *(handle cycles and complex pattern)*

</br>

- ## Property Graphs *(PG)*

    - **Components :**
        - **Nodes :** Entities with **Labels** *(grouping)* and **Properties**
        - **Relationships :** Directed connections with a **Type** and **Properties**
        - **Properties :** Key-Value paris *(`name: "Selym"`, `since: 2026`)* attached to **Nodes** or **Relationships**

    - **PG vs Relational Tables :**
        - **Translation PG -> Tables :** Easy *(Vertex tables, Edge tables)*
        - **Translation Tables -> PG :** Complex if foreign keys are part of primary keys *(N-ary relations)*
        - **Reification :** Transforming a relationship into a node to handle *N-ary* arelations *(`(Alice, Bob, Eve) ` in a `Trouble` relationship)* 
        - **Storage Models :**
            - **Adjacency List *(Native)* :**
                - **Method :** Each node stores refs to adjacent edges, edges store refs to source / target
                - **Used by :** Neo4j
                - **Complexity :** O(1) edge test (local) / Good for traversal (index-free adjacency)
            - **Adjacency Matrix :**
                - **Method :** One matrix per label *(N x N)*
                - **Used by :** RedisGraph *(Sparse matrices)*
                - **Complexity :** O(1) global edge test / High memory usage if not compressed
            - **Edge Tree Sets :**
                - **Method :** Tables indexed by edge type *(Relational approach)*
                - **Complexity :** O(log E)

</br>

- ## Cypher Query Language

    - **Structure :**
        - **Declarative :** ASCI-art style pattern matching
        - **Clauses sequence :** Linear composition (Input Table -> Operation -> Output Table)

    - **Read Clauses :**
        - **MATCH :** Pattern matching using Trail semantics *(Inner Join logic)*
            - ***Node :*** `(n: Label {prop: val})`
            - ***Rel :*** `-[r: TYPE]->`, `<-[r: TYPE]-` (undirected)
            - ***Path :*** `p = (a)-[:REL*]->(b)` *(\* variable length)* 
        - **OPTIONAL MATCH :** Like SQL Left Outer Join *(returns `null` if no match)
        - **WHERE :** Filtering rows
        - **WITH :** Projection, aggregation, or piping results to next clause
        - **RETURN :** Defines the final output colums
        - **ORDER BY / LIMIT / SKIP :** Sorting and pagination

    - **Aggreagation :**
        - **Vertical :** `count(n)`, `sum(n.age)`, `collection(n.name)` *(Implicit grouping in `WITH / RETURN`)*
        - **Horizontal :** `reduce(acc=0, x IN list | acc + x)` *(Aggregate over a list within a row)*

    - **Write Clauses *(ACID compliant)* :**
        - **CREATE :** Creates new nodes / rels *`CREATE (n:User {name:"Selym"})`*
        - **DELETE / DETACH DELETE :** Removes elements *(Detach removes adjacent edges first)*
        - **SET :** Updates labels or properties *`SET n.age = 25`*
        - **MERGE :** "Match or Create" *(Ensures existence without duplicates)*

    - **Query Execution Flow :**
        1. **MATCH** finds patterns in the graph -> generates a tables of matches
        2. **WITH / WHERE** filters or transformes the table
        3. **RETURN** outputs the final table