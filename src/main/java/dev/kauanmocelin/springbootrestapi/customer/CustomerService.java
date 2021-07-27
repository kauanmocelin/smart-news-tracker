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
    private final CustomerMapper customerMapper;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer save(CustomerPostRequestBody customerPostRequestBody) {
        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customerPostRequestBody.getEmail());
        if (customerOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }
        return customerRepository.save(customerMapper.toCustomer(customerPostRequestBody));
    }

    public void delete(Long customerId) {
        boolean customerExists = customerRepository.existsById(customerId);
        if (!customerExists) {
            throw new IllegalStateException("customer with id " + customerId + " does not exists");
        }
        customerRepository.deleteById(customerId);
    }

    public void replace(Long customerId, CustomerPutRequestBody customerPutRequestBody) {
        Customer savedCustomer = findByIdOrThrowIllegalStateException(customerId);

        Optional<Customer> customerOptional = customerRepository.findCustomerByEmail(customerPutRequestBody.getEmail());
        if (customerOptional.isPresent() && !customerOptional.get().getId().equals(savedCustomer.getId())) {
            throw new IllegalStateException("email taken");
        }

        Customer customer = customerMapper.toCustomer(customerPutRequestBody);
        customer.setId(savedCustomer.getId());
        customerRepository.save(customer);
    }

    private Customer findByIdOrThrowIllegalStateException(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalStateException("customer with id " + customerId + " does not exists"));
    }
}
