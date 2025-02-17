package dev.kauanmocelin.springbootrestapi;

import dev.kauanmocelin.springbootrestapi.authentication.registration.request.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.wiremock.integrations.testcontainers.WireMockContainer;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class NewsMonitorServiceE2ETest {

    @LocalServerPort
    private Integer port;

    @Container
    static WireMockContainer wiremockServer = new WireMockContainer("wiremock/wiremock:2.35.1-1")
        .withMappingFromResource("wiremock-mappings/news-api-response.json");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("news.api.url", wiremockServer::getBaseUrl);
    }

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void shouldFetchThreeNewsArticlesWhenJavaKeywordIsProvidedAndUserIsLoggedIn() {
        final var loginRequest = LoginRequest.builder()
            .username("admin@gmail.com")
            .password("123456")
            .build();
        final var accessToken = given()
            .contentType(ContentType.JSON)
            .body(loginRequest)
            .when()
            .post("/api/v1/auth/login")
            .then()
            .statusCode(HttpStatus.OK.value())
            .extract()
            .jsonPath().getString("access_token");
        final var keyword = "java";
        given().contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + accessToken)
            .when()
            .get("/api/v1/news-monitor/news/{keyword}", keyword)
            .then()
            .statusCode(HttpStatus.OK.value())
            .body("status", equalTo("ok"))
            .body("totalResults", equalTo(3))
            .body( "articles", hasSize(3));
    }
}