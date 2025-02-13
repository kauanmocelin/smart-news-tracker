package dev.kauanmocelin.springbootrestapi.authentication.registration;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kauanmocelin.springbootrestapi.authentication.registration.request.RegistrationRequest;
import dev.kauanmocelin.springbootrestapi.common.security.JwtAuthenticationFilter;
import dev.kauanmocelin.springbootrestapi.common.security.JwtService;
import dev.kauanmocelin.springbootrestapi.util.RegistrationPostRequestBodyCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = RegistrationController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class
)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Tests for Registration controller")
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistrationService registrationServiceMock;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @DisplayName("Should return verification code when register new customer")
    void shouldRegisterNewCustomerWhenSuccessful() throws Exception {
        final var expectedCode = "b67477e2-9198-4909-9f2d-cb90dc7b73dc";
        when(registrationServiceMock.register(ArgumentMatchers.any(RegistrationRequest.class))).thenReturn(expectedCode);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(RegistrationPostRequestBodyCreator.createPostRequestBodyCreator())))
            .andExpect(status().isCreated())
            .andExpect(header().string("Location", containsString("/api/v1/auth/verify?token=" + expectedCode)))
            .andExpect(content().string(expectedCode));
    }
}