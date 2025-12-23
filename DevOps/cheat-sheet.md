# DevOps

</br>

- ## Introduction & Concepts :

    - **DevOps Approach (CAMS) :**
        - **C** ulture : Break down walls between Dev and Ops teams
        - **A**utomation : Automate everything (build, deployement, tests)
        - **M**easurement : Monitor technical and business metrics
        - **S**haring : Share knowledge, tools, and code

    - **CI/CD Pipeline :**
        - **CI** *(Continuous Integration)* : Regular code merging, automated unit tests, and builds
        - **CD** *(Continuous Delivery / Deployement)* : Automated deployment to environments (Test, Staging, Production)

</br>

- ## Git :

    - **Essential Commands :**
        ```bash
        git init                    # Initialize a repository
        git clone <url>             # Clone a remote repository
        git status                  # View file status
        git add <file>              # Add file to Index (Staging)
        git commit -m "msg"         # Record changes to the repository
        git push origin <branch>    # Send commits to remote repository
        git pull                    # Fetch and merge remote changes
        ```

    - **Branch Management :**

        ```bash
        git branch                  # List branches
        git branch <name>           # Create a branch
        git switch <name>           # Switch branch (or git checkout)
        git merge <branch>          # Merge a branch into the current one
        ```

</br>

- ## Maven :

    - **Lifecycle :**
        ```bash
        mvn clean       # Removes the target directory
        mvn compile     # Compiles source code
        mvn test        # Runs unit tests
        mvn package     # Creates the artifact (JAR/WAR) in target/
        mvn install     # Installs artifact to local repository (~/.m2)
        ```

</br>

- ## Docker :

    - **Concepts :**
        - **Image :** Read-only template (Class), composed of layers
        - **Container :** Running instance (Object) of an image
        - **Registry :** Storage for images (*Docker Hub*)

    - **Dockerfile :**
        ```docker
        FROM openjdk:17-slim            # Base image
        WORKDIR /app                    # Working directory
        COPY target/app.jar app.jar     # Copy files
        EXPOSE 8080                     # Document the port
        CMD ["java", "-jar", "app.jar"] # Startup command
        ```

    - **CLI Command Line :**
        ```docker
        docker build -t name:tag .  # Build an image
        docker run -p 80:8080 img   # Run container (HostPort:ContainerPort)
        docker ps                   # List running containers
        docker images               # List local images
        docker stop <id>            # Stop a container
        docker rm <id>              # Remove a stopped container
        docker logs -f <id>         # Follow container logs
        ```

</br>

- ## Docker Compose :

    - `docker-compose.yml` : Describes multi container architecture (*App* + *DB*)
        ```yml
        version: '3.1'
        services:
        app:
            build: .                            # Build from current Dockerfile
            ports:
            - "8080:8080"                       # Port mapping
            depends_on:
            - database                          # Startup order

        database:
            image: postgres                     # Official image
            environment:
            POSTGRES_PASSWORD: password
            volumes:
            - pgdata:/var/lib/postgresql/data   # Data persistence

        volumes:
        pgdata:
        ```

    - **Compose Commands :**
        ```bash
        docker-compose up -d        # Start services in background
        docker-compose down         # Stop and remove containers/networks
        docker-compose down -v      # Stop and remove container/networks/volumes
        ```

</br>

- ## Jenkins (Pipeline as Code) :

    - **Jenkinsfile :** Defines CI/CD steps in a Groovy file
        ```groovy
        pipeline {
            agent any                           // Run on any available agent

            stages {
                stage('Build') {
                    steps {
                        bat 'mvn clean compile' // Windows command (sh for Linux)
                    }
                }
                stage('Test') {
                    steps {
                        bat 'mvn test'
                    }
                }
                stage('Deploy') {
                    steps {
                        echo 'Deploying...'
                    }
                }
            }
        }    
        ```

</br>

- ## Flyway (DB Migration) :

    - **Principle :** Version control for database schema like code

    - **Naming Convention :** `V{Version}__{Description}.sql` -> (*`V1__Create_user_table.sql`*)

    - **Behavior :** Flyway executes scripts in version order and never re-runs an already applied script
    