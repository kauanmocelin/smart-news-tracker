package dev.kauanmocelin.springbootrestapi;

import dev.kauanmocelin.springbootrestapi.authentication.registration.dto.RegistrationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@Testcontainers
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.flyway.clean-disabled=false"
)
@ActiveProfiles("test")
@DisplayName("Tests for register/authentication flow end-to-end")
class FullAuthenticationE2EIT {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresSqlContainer = new PostgreSQLContainer<>(DockerImageName.parse(
        "postgres:15.10"
    ));

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @AfterEach()
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    void shouldReturnUnauthorizedWhenMissingAuthorizationCredentials() {
        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/v1/customers")
            .then()
            .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void shouldRegisterNewUserWhenValidInputAndValidationTokenAreProvided() {
        RegistrationRequest registrationRequest = RegistrationRequest.builder()
            .firstName("Fulano")
            .lastName("Da Silva")
            .email("fulano@gmail.com")
            .password("123456")
            .build();
        final var validationToken = given()
            .contentType(ContentType.JSON)
            .body(registrationRequest)
            .when()
            .post("/api/v1/auth/register")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body(notNullValue())
            .extract().body().asString();
        given()
            .when()
            .get("/api/v1/auth/verify?token={validationToken}", validationToken)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(notNullValue());
    }
}
