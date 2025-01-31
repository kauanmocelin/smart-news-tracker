package dev.kauanmocelin.springbootrestapi.customer.mapper;

import dev.kauanmocelin.springbootrestapi.customer.Customer;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "age", ignore = true)
    Customer toCustomer(CustomerPostRequestBody customerPostRequestBody);

    @Mapping(target = "age", ignore = true)
    Customer toCustomer(CustomerPutRequestBody customerPutRequestBody);
}
