package dev.kauanmocelin.springbootrestapi.appuser;

import dev.kauanmocelin.springbootrestapi.appuser.request.AppUserPutRequestBody;
import dev.kauanmocelin.springbootrestapi.util.AppUserCreator;
import dev.kauanmocelin.springbootrestapi.util.CustomerPutRequestBodyCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for AppUser controller")
class AppUserControllerUnitTest {

    @InjectMocks
    private AppUserController appUserController;
    @Mock
    private AppUserService appUserServiceMock;

    @Test
    @DisplayName("Should return list of customer when successful")
    void shouldReturnListOfCustomerWhenSuccessful() {
        when(appUserServiceMock.findAll()).thenReturn(List.of(AppUserCreator.createValidAppUser()));
        var expectedName = AppUserCreator.createValidAppUser().getFirstName();

        List<AppUser> users = appUserController.listAll().getBody();

        assertThat(users)
            .isNotNull()
            .isNotEmpty()
            .hasSize(1);
        assertThat(users.get(0).getFirstName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Should return customer when find by id")
    void shouldReturnCustomerWhenFindById() {
        when(appUserServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
            .thenReturn(AppUserCreator.createValidAppUser());
        Long expectedId = AppUserCreator.createValidAppUser().getId();

        var user = appUserController.findById(1L).getBody();

        assertThat(user).isNotNull();
        assertThat(user.getId())
            .isNotNull()
            .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Should delete customer when successful")
    void shouldDeleteCustomerWhenSuccessful() {
        doNothing().when(appUserServiceMock).delete(ArgumentMatchers.anyLong());
        ResponseEntity<Void> entity = appUserController.deleteCustomer(1L);

        assertThatCode(() -> appUserController.deleteCustomer(1L))
            .doesNotThrowAnyException();
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Should update customer email when successful")
    void shouldUpdateCustomerEmailWhenSuccessful() {
        doNothing().when(appUserServiceMock).replace(ArgumentMatchers.any(AppUserPutRequestBody.class));
        ResponseEntity<Void> entity = appUserController.updateCustomer(CustomerPutRequestBodyCreator.createPutRequestBodyCreator());

        assertThatCode(() -> appUserController.updateCustomer(CustomerPutRequestBodyCreator.createPutRequestBodyCreator()))
            .doesNotThrowAnyException();
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
