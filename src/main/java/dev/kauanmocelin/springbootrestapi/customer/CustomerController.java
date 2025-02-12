package dev.kauanmocelin.springbootrestapi.customer;

import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPutRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    @Operation(summary = "List all customers", description = "List all customer", tags = {"customer"})
    @ApiResponse(responseCode = "200", description = "Successful operation")
    public ResponseEntity<List<Customer>> listAll() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping(path = "/{customer-id}")
    @Operation(summary = "Find customer by Id", description = "Find customer by Id", tags = {"customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Customer does not exist in the database with id")
    })
    public ResponseEntity<Customer> findById(@PathVariable("customer-id") Long customerId) {
        return ResponseEntity.ok(customerService.findByIdOrThrowBadRequestException(customerId));
    }

    @PostMapping
    @Operation(summary = "Register new customer", description = "Register new customer", tags = {"customer"})
    public ResponseEntity<Customer> registerNewCustomer(@RequestBody @Valid CustomerPostRequestBody customerPostRequestBody) {
        return new ResponseEntity<>(customerService.save(customerPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{customer-id}")
    @Operation(summary = "Delete customer", description = "Delete customer", tags = {"customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When customer does no exist in the database")
    })
    public ResponseEntity<Void> deleteCustomer(@PathVariable("customer-id") Long customerId) {
        customerService.delete(customerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping()
    @Operation(summary = "Update customer", description = "Update customer", tags = {"customer"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "When customer does not exist in the database or email already has been used")
    })
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerPutRequestBody customerPutRequestBody) {
        customerService.replace(customerPutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
