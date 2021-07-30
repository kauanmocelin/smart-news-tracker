package dev.kauanmocelin.springbootrestapi.util;

import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPutRequestBody;

public class CustomerPutRequestBodyCreator {

    public static CustomerPutRequestBody createPutRequestBodyCreator() {
        return CustomerPutRequestBody.builder()
                .id(CustomerCreator.createValidUpdatedCustomer().getId())
                .name(CustomerCreator.createValidUpdatedCustomer().getName())
                .email(CustomerCreator.createValidUpdatedCustomer().getEmail())
                .dateOfBirth(CustomerCreator.createValidUpdatedCustomer().getDateOfBirth())
                .build();
    }
}
