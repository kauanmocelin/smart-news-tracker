package dev.kauanmocelin.springbootrestapi.util;

import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;

public class CustomerPostRequestBodyCreator {

    public static CustomerPostRequestBody createPostRequestBodyCreator() {
        return CustomerPostRequestBody.builder()
                .name(CustomerCreator.createCustomerToBeSaved().getName())
                .email(CustomerCreator.createCustomerToBeSaved().getEmail())
                .dateOfBirth(CustomerCreator.createCustomerToBeSaved().getDateOfBirth())
                .build();
    }
}
