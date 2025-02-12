package dev.kauanmocelin.springbootrestapi.appuser;

import dev.kauanmocelin.springbootrestapi.util.AppUserCreator;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Testcontainers
@DataJpaTest
@DisplayName("Tests for AppUser repository")
class AppUserRepositoryTest {

    @Container
    @ServiceConnection
    private static final PostgreSQLContainer<?> postgresSqlContainer = new PostgreSQLContainer<>(DockerImageName.parse(
        "postgres:15.10"
    ));

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Should persist customer when successful")
    void shouldPersistCustomerWhenSuccessful() {
        var appUserToBeSaved = AppUserCreator.createAppUserToBeSaved();

        var appUserSaved = appUserRepository.save(appUserToBeSaved);

        assertThat(appUserSaved).isNotNull();
        assertThat(appUserSaved.getId()).isNotNull();
        assertThat(appUserSaved.getFirstName()).isEqualTo(appUserToBeSaved.getFirstName());
    }

    @Test
    @DisplayName("Should return modified first name when update user first name")
    void shouldUpdateCustomerNameWhenSuccessful() {
        var appUserToBeSaved = AppUserCreator.createAppUserToBeSaved();
        var appUserSaved = entityManager.persistAndFlush(appUserToBeSaved);
        appUserSaved.setFirstName("Joao");

        var appUserUpdated = appUserRepository.save(appUserSaved);

        assertThat(appUserUpdated).isNotNull();
        assertThat(appUserUpdated.getId()).isNotNull();
        assertThat(appUserUpdated.getFirstName()).isEqualTo(appUserSaved.getFirstName());
    }

    @Test
    @DisplayName("Should return nothing when user was deleted")
    void shouldDeleteCustomerWhenSuccessful() {
        var appUserToBeSaved = AppUserCreator.createAppUserToBeSaved();
        var appUserSaved = entityManager.persistAndFlush(appUserToBeSaved);
        entityManager.remove(appUserSaved);

        Optional<AppUser> userOptional = appUserRepository.findById(appUserSaved.getId());

        assertThat(userOptional).isEmpty();
    }

    @Test
    @DisplayName("Should return user when found by email")
    void shouldFindCustomerByEmailWhenSuccessful() {
        var appUserToBeSaved = AppUserCreator.createAppUserToBeSaved();
        var appUserSaved = entityManager.persistAndFlush(appUserToBeSaved);
        var emailOfSavedAppUser = appUserSaved.getEmail();

        Optional<AppUser> customerOptional = appUserRepository.findByEmail(emailOfSavedAppUser);

        assertThat(customerOptional)
            .isPresent()
            .get().hasFieldOrPropertyWithValue("email", emailOfSavedAppUser);
    }

    @Test
    @DisplayName("Should return nothing when not found user by email ")
    void shouldNotFindCustomerByEmailWhenCustomerIsNotFound() {
        Optional<AppUser> customerOptional = appUserRepository.findByEmail("email@email.com");

        assertThat(customerOptional).isEmpty();
    }

    @Test
    @DisplayName("Should throw ConstraintViolationException on save when email is empty")
    void shouldThrowConstraintViolationExceptionOnSaveWhenEmailIsEmpty() {
        var appUser = new AppUser();

        assertThatThrownBy(() -> appUserRepository.save(appUser))
            .isInstanceOf(ConstraintViolationException.class)
            .hasMessageContaining("The user e-mail cannot be empty");
    }
}