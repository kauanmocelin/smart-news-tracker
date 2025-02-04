package dev.kauanmocelin.springbootrestapi;

import dev.kauanmocelin.springbootrestapi.customer.Customer;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPutRequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
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
    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse(
        "mysql:8.1.0"
    ));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @LocalServerPort
    private int port;

    @BeforeEach
    void clearDatabase(@Autowired Flyway flyway) {
        flyway.clean();
        flyway.migrate();
    }

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldCreateNewCustomerWithSuccessWhenValidRequestIsSent() {
        final var customerToSave = CustomerPostRequestBody.builder()
            .name("Fulano da Silva")
            .email("fulano@gmail.com")
            .dateOfBirth(LocalDate.of(2000, 1, 1))
            .build();
        given()
            .contentType(ContentType.JSON)
            .body(customerToSave)
            .when()
            .post("/api/v1/customers")
            .then()
            .statusCode(HttpStatus.CREATED.value())
            .body("name", equalTo("Fulano da Silva"))
            .body("email", equalTo("fulano@gmail.com"))
            .body("dateOfBirth", equalTo("2000-01-01"));
    }

    @Test
    void shouldReturnCustomerWhenHeExistInDatabase() {
        final var customerToSave = CustomerPostRequestBody.builder()
            .name("Fulano da Silva")
            .email("fulano@gmail.com")
            .dateOfBirth(LocalDate.of(2000, 1, 1))
            .build();
        final var idSavedCustomer = given()
            .contentType(ContentType.JSON)
            .body(customerToSave)
            .when()
            .post("/api/v1/customers")
            .then()
            .extract()
            .jsonPath().getLong("id");

        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/v1/customers/{customerId}", idSavedCustomer)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("email", equalTo("fulano@gmail.com"));
    }

    @Test
    void shouldReturnAllCustomersWhenTheyExistInDatabase() {
        final var customerToSave = CustomerPostRequestBody.builder()
            .name("Fulano da Silva")
            .email("fulano@gmail.com")
            .dateOfBirth(LocalDate.of(2000, 1, 1))
            .build();
        final var anotherCustomerToSave = CustomerPostRequestBody.builder()
            .name("Jose Pereira")
            .email("jose@hotmail.com")
            .dateOfBirth(LocalDate.of(1985, 3, 1))
            .build();
        given()
            .contentType(ContentType.JSON)
            .body(customerToSave)
            .when()
            .post("/api/v1/customers");
        given()
            .contentType(ContentType.JSON)
            .body(anotherCustomerToSave)
            .when()
            .post("/api/v1/customers");
        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/v1/customers")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", equalTo(2))
            .body("name", hasItems("Fulano da Silva", "Jose Pereira"))
            .body("email", hasItems("fulano@gmail.com", "jose@hotmail.com"));
    }

    @Test
    void shouldUpdateCustomerBirthDateWhenValidRequestIsSent() {
        final var customerToSave = CustomerPostRequestBody.builder()
            .name("Fulano da Silva")
            .email("fulano@gmail.com")
            .dateOfBirth(LocalDate.of(2000, 1, 1))
            .build();
        final var idSavedCustomer = given()
            .contentType(ContentType.JSON)
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
            .body(customerToUpdate)
            .when()
            .put("/api/v1/customers")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/v1/customers/{customerId}", idSavedCustomer)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body(notNullValue())
            .body("dateOfBirth", equalTo(LocalDate.of(1999, 2, 1).toString()));
    }

    @Test
    void shouldDeleteCustomerSuccessfullyWhenCustomerIsPresentInDatabase() {
        final var customerToSave = CustomerPostRequestBody.builder()
            .name("Fulano da Silva")
            .email("fulano@gmail.com")
            .dateOfBirth(LocalDate.of(2000, 1, 1))
            .build();
        given()
            .contentType(ContentType.JSON)
            .body(customerToSave)
            .when()
            .post("/api/v1/customers");
        final List<Customer> customers = given()
            .contentType(ContentType.JSON)
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
            .when()
            .delete("/api/v1/customers/{idCustomerToDelete}", customers.getFirst().getId())
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/v1/customers")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", equalTo(0));
    }
}
