package dev.kauanmocelin.springbootrestapi.customer;

import dev.kauanmocelin.springbootrestapi.customer.mapper.CustomerMapper;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public void addNewCustomer(CustomerPostRequestBody customerPostRequestBody) {
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customerPostRequestBody.getEmail());
        if (customerOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        customerRepository.save(CustomerMapper.INSTANCE.toCustomer(customerPostRequestBody));
    }

    public void deleteCustomer(Long customerId) {
        boolean customerExists = customerRepository.existsById(customerId);
        if (!customerExists) {
            throw new IllegalStateException("customer with id " + customerId + " does not exists");
        }
        customerRepository.deleteById(customerId);
    }

    public void updateCustomer(Long customerId, CustomerPutRequestBody customerPutRequestBody) {
        Customer savedCustomer = findByIdOrThrowIllegalStateException(customerId);

        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customerPutRequestBody.getEmail());
        if (customerOptional.isPresent() && !customerOptional.get().getId().equals(savedCustomer.getId())) {
            throw new IllegalStateException("email taken");
        }

        Customer customer = CustomerMapper.INSTANCE.toCustomer(customerPutRequestBody);
        customer.setId(savedCustomer.getId());
        customerRepository.save(customer);
    }

    private Customer findByIdOrThrowIllegalStateException(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("customer with id " + customerId + " does not exists"));
    }
}
