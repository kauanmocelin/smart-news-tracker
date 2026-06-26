# SmartNews Tracker

## Overview
SmartNews Tracker is an API that allows users to monitor news based on specific keywords. The system periodically queries a news API and sends email alerts whenever new articles matching the keywords are found.

## Technologies
- Java 21
- Spring Boot/Data JPA/Security
- Flyway
- Maven
- Lombok/MapStruct
- Testcontainers
- Wiremock
- Docker
- SpringDoc - Open API 3

## Features
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

## Getting Started

### Prerequisites
- Java 21
- Docker

### Generate JWT secret
Generate a 32-byte secret key at https://jwtsecret.com/generate

### Get News API key
Create an API key at https://newsapi.org/

### Configure environment variables
Rename `.env.example` to `.env` and set:

- `JWT_SECRET_KEY`
- `NEWS_API_KEY`

### Build and Running Locally from docker
1. Clone the repository to your local machine:
    ```
    git clone https://github.com/kauanmocelin/smart-news-tracker.git
    ```
2. Go to the project directory:
    ```
    cd smart-news-tracker
    ```
3. Starting with a script that builds and starts the REST API:
    ```
    scripts/run.sh
    ```
4. Access the api documentation:
    ```
    http://localhost:8080/swagger-ui/index.html
    ```

### Running from docker image
Please follow the instructions at the [docker hub repository.](https://hub.docker.com/r/kauanmocelin/smartnewstracker)

## Contributing
Contributions are welcome. If you have suggestions, bug reports, or feature requests, please open an issue or submit a pull request.

## License
This project is licensed under the MIT License. See the [LICENSE](./LICENSE) file for more details.

## Author
**Kauan Mocelin**

[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/kauanmocelin/)
