package dev.kauanmocelin.springbootrestapi.customer.mapper;

import dev.kauanmocelin.springbootrestapi.customer.Customer;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPutRequestBody;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerPostRequestBody customerPostRequestBody);

    Customer toCustomer(CustomerPutRequestBody customerPutRequestBody);
}
