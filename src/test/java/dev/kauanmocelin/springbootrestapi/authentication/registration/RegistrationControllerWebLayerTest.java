package dev.kauanmocelin.springbootrestapi.authentication.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kauanmocelin.springbootrestapi.authentication.registration.dto.RegistrationRequest;
import dev.kauanmocelin.springbootrestapi.security.JwtAuthenticationFilter;
import dev.kauanmocelin.springbootrestapi.util.RegistrationPostRequestBodyCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    value = RegistrationController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class,
    excludeFilters =
    @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = JwtAuthenticationFilter.class))
@DisplayName("Tests for Registration controller")
class RegistrationControllerWebLayerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistrationService registrationServiceMock;

    @MockitoBean
    private LogoutHandler logoutHandlerMock;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return verification code when register new customer")
    void shouldRegisterNewCustomerWhenSuccessful() throws Exception {
        final var expectedCode = "b67477e2-9198-4909-9f2d-cb90dc7b73dc";
        when(registrationServiceMock.register(any(RegistrationRequest.class))).thenReturn(expectedCode);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(RegistrationPostRequestBodyCreator.createPostRequestBodyCreator())))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/api/v1/auth/verify?token=" + expectedCode)))
            .andExpect(content().string(expectedCode));
    }

    @Test
    @DisplayName("First name is not empty")
    void shouldReturnExceptionBadRequestWhenNameIsNotProvided() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RegistrationPostRequestBodyCreator.createPostRequestBodyWithoutNameCreator())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldsErrorValidation[*].message")
                .value(hasItem("The user first name cannot be empty")));
    }

    @Test
    @DisplayName("Name cannot be shorter than 3 characters")
    void shouldReturnExceptionBadRequestWhenNameIsOnlyTwoCharacters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RegistrationPostRequestBodyCreator.createPostRequestBodyWithInvalidNameCreator())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldsErrorValidation[*].message")
            .value(hasItem("The user first name must be between 3 and 100 characters")));
    }

    @Test
    @DisplayName("Email should be valid")
    void shouldReturnExceptionBadRequestWhenEmailIsInvalid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(RegistrationPostRequestBodyCreator.createPostRequestBodyWithInvalidEmailCreator())))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.fieldsErrorValidation[*].message")
            .value(hasItem("The user email should be valid")));
    }
}