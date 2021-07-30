package dev.kauanmocelin.springbootrestapi.customer;

import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPutRequestBody;
import dev.kauanmocelin.springbootrestapi.util.CustomerCreator;
import dev.kauanmocelin.springbootrestapi.util.CustomerPostRequestBodyCreator;
import dev.kauanmocelin.springbootrestapi.util.CustomerPutRequestBodyCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@ExtendWith(SpringExtension.class)
class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;
    @Mock
    private CustomerService customerServiceMock;

    @BeforeEach
    void setUp() {
        List<Customer> customers = List.of(CustomerCreator.createValidCustomer());
        BDDMockito.when(customerServiceMock.findAll())
                .thenReturn(customers);

        BDDMockito.when(customerServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(CustomerCreator.createValidCustomer());

        BDDMockito.when(customerServiceMock.save(ArgumentMatchers.any(CustomerPostRequestBody.class)))
                .thenReturn(CustomerCreator.createValidCustomer());

        BDDMockito.doNothing().when(customerServiceMock).delete(ArgumentMatchers.anyLong());

        BDDMockito.doNothing().when(customerServiceMock).replace(ArgumentMatchers.any(CustomerPutRequestBody.class));
    }

    @Test
    @DisplayName("Should return list of customer when successful")
    void shouldReturnListOfCustomerWhenSuccessful() {
        String expectedName = CustomerCreator.createValidCustomer().getName();

        List<Customer> customers = customerController.listAll().getBody();

        assertThat(customers)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        assertThat(customers.get(0).getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Should return customer when find by id")
    void shouldReturnCustomerWhenFindById() {
        Long expectedId = CustomerCreator.createValidCustomer().getId();

        Customer customer = customerController.findById(1L).getBody();

        assertThat(customer).isNotNull();
        assertThat(customer.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Should register new customer when successful")
    void shouldRegisterNewCustomerWhenSuccessful() {
        Customer customer = customerController.registerNewCustomer(CustomerPostRequestBodyCreator.createPostRequestBodyCreator()).getBody();

        assertThat(customer)
                .isNotNull()
                .isEqualTo(CustomerCreator.createValidCustomer());
    }

    @Test
    @DisplayName("Should delete customer when successful")
    void shouldDeleteCustomerWhenSuccessful() {
        ResponseEntity<Void> entity = customerController.deleteCustomer(1L);

        assertThatCode(() -> customerController.deleteCustomer(1L))
                .doesNotThrowAnyException();
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("Should update customer email when successful")
    void shouldUpdateCustomerEmailWhenSuccessful() {
        ResponseEntity<Void> entity = customerController.updateCustomer(CustomerPutRequestBodyCreator.createPutRequestBodyCreator());

        assertThatCode(() -> customerController.updateCustomer(CustomerPutRequestBodyCreator.createPutRequestBodyCreator()))
                .doesNotThrowAnyException();
        assertThat(entity).isNotNull();
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}