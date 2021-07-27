package dev.kauanmocelin.springbootrestapi.customer;

import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<Customer> getCustomers() {
        return customerService.getCustomers();
    }

    @PostMapping
    public void registerNewCustomer(@RequestBody CustomerPostRequestBody customerPostRequestBody) {
        customerService.addNewCustomer(customerPostRequestBody);
    }

    @DeleteMapping(path = "{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    @PutMapping(path = "{customerId}")
    public void updateCustomer(@PathVariable("customerId") Long customerId, @RequestBody CustomerPutRequestBody customerPutRequestBody) {
        customerService.updateCustomer(customerId, customerPutRequestBody);
    }
}
