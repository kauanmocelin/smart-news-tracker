# SmartNews Tracker

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/kauanmocelin/rest-api-springboot/blob/main/LICENSE)

# About

SmartNews Tracker is an API that allows users to monitor news based on specific keywords. The system periodically queries a news API and sends email alerts whenever new articles matching the keywords are found.

# Prerequisites

- Java 21
- Docker

# Technologies

- Java 21
- Spring Boot/Data JPA/Security
- Flyway
- Maven
- Lombok/MapStruct
- Testcontainers
- Wiremock
- Docker
- SpringDoc - Open API 3

# Features

- User signup with email verification
- User email verification flow (send verification link, verify user)
- User login with JWT token/refresh token
- User logout 
- Users account can be managed by Admin

# Running

```bash
# clone repository
git clone https://github.com/kauanmocelin/rest-api-springboot.git

# enter the project folder
cd rest-api-springboot

# run docker container
docker-compose up

# execute the project
./mvnw spring-boot:run

# access the api documentation
http://127.0.0.1:8080/swagger-ui.html 
```

# Author

**Kauan Mocelin**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/kauanmocelin/)
