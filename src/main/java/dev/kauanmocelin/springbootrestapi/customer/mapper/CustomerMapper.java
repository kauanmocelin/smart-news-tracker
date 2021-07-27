package dev.kauanmocelin.springbootrestapi.customer.mapper;

import dev.kauanmocelin.springbootrestapi.customer.Customer;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;
import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class CustomerMapper {
    public static final CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    public abstract Customer toCustomer(CustomerPostRequestBody customerPostRequestBody);

    public abstract Customer toCustomer(CustomerPutRequestBody customerPutRequestBody);
}
