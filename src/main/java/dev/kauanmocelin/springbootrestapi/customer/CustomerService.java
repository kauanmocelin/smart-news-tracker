package dev.kauanmocelin.springbootrestapi.customer;

import dev.kauanmocelin.springbootrestapi.customer.exception.BadRequestException;
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

    public Customer findByIdOrThrowBadRequestException(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new BadRequestException("customer with id " + customerId + " does not exists"));
    }

    public Customer save(CustomerPostRequestBody customerPostRequestBody) {
        Optional<Customer> customerOptional = customerRepository.findByEmail(customerPostRequestBody.getEmail());
        if (customerOptional.isPresent()) {
            throw new BadRequestException("email taken");
        }
        return customerRepository.save(customerMapper.toCustomer(customerPostRequestBody));
    }

    public void delete(Long customerId) {
        boolean customerExists = customerRepository.existsById(customerId);
        if (!customerExists) {
            throw new BadRequestException("customer with id " + customerId + " does not exists");
        }
        customerRepository.deleteById(customerId);
    }

    public void replace(CustomerPutRequestBody customerPutRequestBody) {
        Customer savedCustomer = findByIdOrThrowBadRequestException(customerPutRequestBody.getId());

        Optional<Customer> customerOptional = customerRepository.findByEmail(customerPutRequestBody.getEmail());
        if (customerOptional.isPresent() && !customerOptional.get().getId().equals(savedCustomer.getId())) {
            throw new BadRequestException("email taken");
        }

        Customer customer = customerMapper.toCustomer(customerPutRequestBody);
        customer.setId(savedCustomer.getId());
        customerRepository.save(customer);
    }
}
