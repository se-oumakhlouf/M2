# APIs

</br>

- ## Introduction & Concepts :

    - **Definition(API) :** **A**pplication **P**rogramming **I**nterface
        - An interface allowing multiple non-native applications to interact
        - **Analogy :** While a phone book allows a human to request information from another human via telephone, an API allows a computer to request information from another computer via the internet

    - **Core functions :**
        - Exposes data in a structured format
        - Fixes interaction protocals for data exchange
        - Provides documentation for developers

    - **What an API is NOT :**
        - a software
        - a user interface (UI)
        - a server

</br>

- ## API Architecture & Types :

    - **Standard Protocols / Styles :**
        - **SOAP :** Information exchange protocol implemented in web services using XML
        - **REST :** Architectural style for web services operating as a communication chain

    - **Request Workflow :**
        1. Clients sends a data request to the server
        2. Server extracts data from applications and translates it into a universal language via the API interface
        3. Data is returned to the server and transmitted back to the client

</br>

- ## Design Methodology (DDD) :

    - **Domain-Driven Design *(DDD)* :** Approach to describe a *business domain* and create a shared vision between stakeholders

    - **Essential Notions :**
        - **Bounded Context :** Defined boundaries of the domain
        - **User Stories :** The specific user needs to be addressed 
            - *En tant que ..., je souhaite ... afin de ...*
            - *As a ..., I want ... in order to ...*
        - **Ubiquitous Language :** Common language used by everyone on the project
    
    - **Domain Model Components :**
        - **Aggregate Roots :** Objects with an identity, representing a unit of consistency *(a bank account)*
        - **Domain Services :** Operations acting on multiple objects *(a banking transaction)*
        - **Specifications :** Represent sets of criteria and allow the expression of business rules
        - **Events :** Capture significant occurences that have happened with the domain

</br>

- ##