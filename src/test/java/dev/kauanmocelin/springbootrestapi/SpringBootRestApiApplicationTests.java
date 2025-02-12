/*
package dev.kauanmocelin.springbootrestapi;

import dev.kauanmocelin.springbootrestapi.authentication.registration.request.RegistrationRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Testcontainers
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.flyway.clean-disabled=false"
)
@ActiveProfiles("test")
class SpringBootRestApiApplicationTests {

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
    @Sql("data.sql")
    void shouldCreateNewCustomerWithSuccessWhenValidRequestIsSent() {
        final var customerToSave = new CustomerPostRequestBody(
            "Fulano da Silva",
            "fulano@gmail.com",
            LocalDate.of(2000, 1, 1));
        given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .body(customerToSave)
            .log().all()
            .when()
            .post("/api/v1/customers")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("name", equalTo("Fulano da Silva"))
            .body("email", equalTo("fulano@gmail.com"))
            .body("dateOfBirth", equalTo("2000-01-01"));
    }

    @Test
    @Sql("data.sql")
    void shouldReturnCustomerWhenHeExistInDatabase() {
        final var customerToSave = new CustomerPostRequestBody(
            "Fulano da Silva",
            "fulano@gmail.com",
            LocalDate.of(2000, 1, 1));
        final var idSavedCustomer = given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .body(customerToSave)
            .when()
            .post("/api/v1/customers")
            .then()
            .extract()
            .jsonPath().getLong("id");

        given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .when()
            .get("/api/v1/customers/{customerId}", idSavedCustomer)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("email", equalTo("fulano@gmail.com"));
    }

    @Test
    @Sql("data.sql")
    void shouldReturnAllCustomersWhenTheyExistInDatabase() {
        final var customerToSave = new CustomerPostRequestBody(
            "Fulano da Silva",
            "fulano@gmail.com",
            LocalDate.of(2000, 1, 1));
        final var anotherCustomerToSave = new CustomerPostRequestBody(
            "Jose Pereira",
            "jose@hotmail.com",
            LocalDate.of(1985, 3, 1));
        given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .body(customerToSave)
            .when()
            .post("/api/v1/customers");
        given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .body(anotherCustomerToSave)
            .when()
            .post("/api/v1/customers");
        given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .when()
            .get("/api/v1/customers")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", equalTo(2))
            .body("name", hasItems("Fulano da Silva", "Jose Pereira"))
            .body("email", hasItems("fulano@gmail.com", "jose@hotmail.com"));
    }

    @Test
    @Sql("data.sql")
    void shouldUpdateCustomerBirthDateWhenValidRequestIsSent() {
        final var customerToSave = new CustomerPostRequestBody(
            "Fulano da Silva",
            "fulano@gmail.com",
            LocalDate.of(2000, 1, 1));
        final var idSavedCustomer = given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .body(customerToSave)
            .when()
            .post("/api/v1/customers")
            .then()
            .extract()
            .jsonPath().getLong("id");
        CustomerPutRequestBody customerToUpdate = CustomerPutRequestBody.builder()
            .id(idSavedCustomer)
            .name("Fulano da Silva")
            .email("fulano@gmail.com")
            .dateOfBirth(LocalDate.of(1999, 2, 1))
            .build();
        given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .body(customerToUpdate)
            .when()
            .put("/api/v1/customers")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
        given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .when()
            .get("/api/v1/customers/{customerId}", idSavedCustomer)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(notNullValue())
            .body("dateOfBirth", equalTo(LocalDate.of(1999, 2, 1).toString()));
    }

    @Test
    @Sql("data.sql")
    void shouldDeleteCustomerSuccessfullyWhenCustomerIsPresentInDatabase() {
        final var customerToSave = new CustomerPostRequestBody(
            "Fulano da Silva",
            "fulano@gmail.com",
            LocalDate.of(2000, 1, 1));
        given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .body(customerToSave)
            .when()
            .post("/api/v1/customers");
        final List<Customer> customers = given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .when()
            .get("/api/v1/customers")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", equalTo(1))
            .extract()
            .jsonPath()
            .getList("", Customer.class);
        given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .when()
            .delete("/api/v1/customers/{idCustomerToDelete}", customers.getFirst().getId())
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
        given()
            .contentType(ContentType.JSON)
            .auth().basic("user@gmail.com", "pass")
            .when()
            .get("/api/v1/customers")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", equalTo(0));
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
            .statusCode(HttpStatus.OK.value())
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
*/
