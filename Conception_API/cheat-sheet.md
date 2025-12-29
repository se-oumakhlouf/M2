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

- ## Service Architecture Layers :

    - **Service Definition :** A software component designed to provide value-added functionality within a business domain, acessible via an interface

    - **Hierarchy :**
        - **Service :** Can group multiple operations
        - **Operation :** Can be exposed in defferent forms
        - **Exposition :** Depends on a technical contract *(API, Kafka, ...)*
        
    - **The 4-Layer Model *(Scalability & Reusability)* :**
        1. **Interface Layer :** Exposes operations *(API, Kafka messages)* to channels *(Internet, Partners)*
        2. **Application Layer :** Orchestrates activity within the services
        3. **Domain Layer :** Holds business logic and specific management rules
        4. **Infrastructure Layer :** Manages communication with the surrounding ecosystem *(DB access, external API calls)*

</br>

- ## Service Design Best Practices :

    - **Robustness :**
        - Must be **tolerant to changes** in dependent services
        - Must maintain **backward compatibility** whenever possible
        - **Design For Failure :** Manage failure cases gracefully

    - **Management :**
        - Maintain an up-to-date map of consumers
        - Communicate interface updates
        - Implement technical and application monitoring

    - **Security :** The service carries its own security and authorization rules

</br>

- ## REST Standards *(Representational State Transfer)* :

    - **Concept :** A set of conventions for distributed architecture where resources are manipulated via standardized verbs *(HTTP)*

    - **Stateless :** Request processing does not require knowledge of previous requests

</br>

- ## REST Implementation Rules :

    - **URI Construction :**
      `scheme:[//authority]/[version]path[?query][#fragment]`
        - **Scheme:** `http(s)`
        - **Authority:** `host:port`
        - **Path:** Hierarchical relationship composed of:
            - **Base-path :** Functional domain of the API
            - **Version :** Format `/vX` (where X is a number).
            - **Endpoint-path :** Resources and sub-resources.
        - **Query:** Non-hierarchical parameters (`key=value`), separated by `&`.
        - **Fragment:** Preceded by `#`, identifies a sub-part of the resource

    - **HTTP Verbs Usage :**
        - **GET :** Read/Consult resources *(Body must be empty)*
        - **POST :** Process or Create *(Server calculates the ID)*
        - **PUT :** Create or Full Update *(Client forces the ID)*
        - **PATCH :** Partial update of attributes
        - **DELETE :** Remove resource *(Body must be empty)*

    - **Status Codes :**
        - **2xx :** Success
        - **3xx :** Redirection
        - **400 :** Bad Request *(Validation error)*
        - **401 :** Unauthorized *(Not verified)*
        - **403 :** Forbidden *(Verified but not allowed)*
        - **404 :** Not Found
        - **5xx :** Service Unavailable *(Server side error)*

</br>

- ## Resilience Patterns *(Design for Failure) *:

    - **Self-Protection *(Protecting the Service)* :**
        - **Throttling *(Rate Limiting)* :** Limits the number of calls per time interval. Calls exceeding the limit are queued or rejected
        - **Bulkhead :** Isolate internal resources for different features. A failure in one feature does not crash the entire service

    - **Dependency Management *(Protecting from External Failures)* :**
        - **Timeout :** Interrupts a call after a maximum defined duration
        - **Retry :** Retrying a failed call in hopes of reaching an operational component
        - **Circuit Breaker :** Monitor error rates
            - If errors exceed a threshold, it triggers a **Fallback**
            - It automatically tests for recovery to return to normal
        - **Idempotency :**
            - **Definition :** Ensuring that *n* calls to the same service produce the same result
            - **Implementation :** Use a unique key for each call. If the server receives a duplicate key, it knows the operation was already performed

</br>

- ## Security Fundamentals :

    - **The 3 Pillars :**
        1. **Authentication *(AuthN)* :** Knowing **WHO** is calling
        2. **Authorization *(AuthZ)* :** Knowing **WHAT** the caller is allowed to do
        3. **Traceability :** Knowing who did what, when and with which resources

    - **Basic Protection :**
        - **HTTPS :** Mandatory for all network communications to ensure confidentiality and integrity
        - **API Key :**
            - A key given to a developer to identify the **application** *(not the user)*
            - Used for quotas / throttling
            - **Limit :** No expiration date. If compromised, the app is compromised

</br>

- ## OAuth2 & OpenID Connect *(OIDC)* :

    - **Definitions :**
        - **OAuth2 :** Protocol dedicated to the delegation of rights *(Authorization)*. It allows an app to act on behalf of a user
        - **OIDC :** Extension of OAuth2 dedicated to **Identity Management** *(Authentication)*

    - **Roles / Actors :**
        - **Resource Owner :** The end-user
        - **Client :** The application consuming the API
        - **Authorization Server :** Issues tokens
        - **Resource Server :** The API hosting the data

    - **The Token :**
        - Opaque information linking a client, a scope *(permission)* and optionally a user
        - Must be sent in the **Header** -> `Authorization: Bearer <token>`

    - **Client Credentials Grant *(Server to Server Flow)* :**
        - Used for back-end interactions without user intervention
        1.  **Client** sends `client_id` + `client_secret` to **Auth Server**
        2. **Auth Server** returns an `access_token` *(HTTP 200)*
        3. **Client** calls the API with the token