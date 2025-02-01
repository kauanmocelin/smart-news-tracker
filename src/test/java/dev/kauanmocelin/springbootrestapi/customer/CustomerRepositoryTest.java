package dev.kauanmocelin.springbootrestapi.customer;

import dev.kauanmocelin.springbootrestapi.util.CustomerCreator;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@DisplayName("Tests for Customer Repository")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("Should persist customer when successful")
    void shouldPersistCustomerWhenSuccessful() {
        Customer customerToBeSaved = CustomerCreator.createCustomerToBeSaved();

        Customer customerSaved = this.customerRepository.save(customerToBeSaved);

        assertThat(customerSaved).isNotNull();
        assertThat(customerSaved.getId()).isNotNull();
        assertThat(customerSaved.getName()).isEqualTo(customerToBeSaved.getName());
    }

    @Test
    @DisplayName("Should update customer name when successful")
    void shouldUpdateCustomerNameWhenSuccessful() {
        Customer customerToBeSaved = CustomerCreator.createCustomerToBeSaved();

        Customer customerSaved = this.customerRepository.save(customerToBeSaved);

        customerSaved.setName("Joao");
        Customer customerUpdated = this.customerRepository.save(customerSaved);

        assertThat(customerUpdated).isNotNull();
        assertThat(customerUpdated.getId()).isNotNull();
        assertThat(customerUpdated.getName()).isEqualTo(customerSaved.getName());
    }

    @Test
    @DisplayName("Should delete customer when successful")
    void shouldDeleteCustomerWhenSuccessful() {
        Customer customerToBeSaved = CustomerCreator.createCustomerToBeSaved();

        Customer customerSaved = this.customerRepository.save(customerToBeSaved);

        this.customerRepository.delete(customerSaved);

        Optional<Customer> customerOptional = this.customerRepository.findById(customerSaved.getId());

        assertThat(customerOptional).isEmpty();
    }

    @Test
    @DisplayName("Should find customer by email when successful")
    void shouldFindCustomerByEmailWhenSuccessful() {
        Customer customerToBeSaved = CustomerCreator.createCustomerToBeSaved();

        Customer customerSaved = this.customerRepository.save(customerToBeSaved);

        String email = customerSaved.getEmail();
        Optional<Customer> customerOptional = this.customerRepository.findByEmail(email);

        assertThat(customerOptional)
                .isPresent()
                .get().hasFieldOrPropertyWithValue("email", email);
    }

    @Test
    @DisplayName("Should not find customer by email when customer is not found")
    void shouldNotFindCustomerByEmailWhenCustomerIsNotFound() {
        Optional<Customer> customerOptional = this.customerRepository.findByEmail("email@email.com");

        assertThat(customerOptional).isEmpty();
    }

    @Test
    @DisplayName("Should throw ConstraintViolationException on save when email is empty")
    void shouldThrowConstraintViolationExceptionOnSaveWhenEmailIsEmpty() {
        Customer customer = new Customer();

        assertThatThrownBy(() -> this.customerRepository.save(customer))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("The customer e-mail cannot be empty");
    }
}