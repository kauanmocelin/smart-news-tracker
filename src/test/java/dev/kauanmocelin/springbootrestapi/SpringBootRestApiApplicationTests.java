package dev.kauanmocelin.springbootrestapi;

import dev.kauanmocelin.springbootrestapi.customer.Customer;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPutRequestBody;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.LocalDate;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class SpringBootRestApiApplicationTests {

    @BeforeEach
    void clearDatabase(@Autowired JdbcTemplate jdbcTemplate) {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "customer");
    }

    @LocalServerPort
    private int port;

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
            .post("/api/v1/customer")
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
            .post("/api/v1/customer")
            .then()
            .extract()
            .jsonPath().getLong("id");

        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/v1/customer/{customerId}", idSavedCustomer)
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
            .post("/api/v1/customer");
        given()
            .contentType(ContentType.JSON)
            .body(anotherCustomerToSave)
            .when()
            .post("/api/v1/customer");
        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/v1/customer")
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
            .post("/api/v1/customer")
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
            .put("/api/v1/customer")
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/v1/customer/{customerId}", idSavedCustomer)
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
            .post("/api/v1/customer");
        final List<Customer> customers = given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/v1/customer")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", equalTo(1))
            .extract()
            .jsonPath()
            .getList("", Customer.class);
        given()
            .contentType(ContentType.JSON)
            .when()
            .delete("/api/v1/customer/{idCustomerToDelete}", customers.get(0).getId())
            .then()
            .statusCode(HttpStatus.NO_CONTENT.value());
        given()
            .contentType(ContentType.JSON)
            .when()
            .get("/api/v1/customer")
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("size()", equalTo(0));
    }
}
