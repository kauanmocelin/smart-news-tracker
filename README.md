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

- Authentication
  - User signup with email verification
  - User email verification flow (send verification link, verify user)
  - User login with JWT token/refresh token
  - User logout
- Business
  - Users accounts can be managed by admin
  - User can register keyword for monitoring
  - User receive scheduled emails summarizing ten most popular news monitored
  - User can fetch ten most popular news with a keyword

# Running

1. generate a JWT secret key on [JwtSecret.com](https://jwtsecret.com/generate) with 32bytes
2. for news service use [newsAPI](https://newsapi.org/) and get an api key 
3. rename `.env.example` for `.env`
4. fill variables `JWT_SECRET_KEY` and `NEWS_API_KEY` with values obtained in steps 1 and 2

```bash
# clone repository
git clone https://github.com/kauanmocelin/smart-news-tracker.git

# enter the project folder
cd smart-news-tracker

# run docker container
docker-compose up

# execute the project
./mvnw spring-boot:run

# access the api documentation
http://localhost:8080/swagger-ui/index.html 
```

# Author

**Kauan Mocelin**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/kauanmocelin/)
