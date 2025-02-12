package dev.kauanmocelin.springbootrestapi.authentication.registration;

import dev.kauanmocelin.springbootrestapi.authentication.registration.request.RegistrationRequest;
import dev.kauanmocelin.springbootrestapi.util.RegistrationPostRequestBodyCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Registration controller")
class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;
    @Mock
    private RegistrationService registrationServiceMock;

    @Test
    @DisplayName("Should return verification code when register new customer")
    void shouldRegisterNewCustomerWhenSuccessful() {
        when(registrationServiceMock.register(ArgumentMatchers.any(RegistrationRequest.class)))
            .thenReturn("b67477e2-9198-4909-9f2d-cb90dc7b73dc");

        var verificationCode = registrationController.register(RegistrationPostRequestBodyCreator.createPostRequestBodyCreator());

        assertThat(verificationCode)
            .isNotNull()
            .isEqualTo(verificationCode);
    }
}