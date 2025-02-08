package dev.kauanmocelin.springbootrestapi.util;

import dev.kauanmocelin.springbootrestapi.customer.request.CustomerPostRequestBody;

import java.time.LocalDate;

public class CustomerPostRequestBodyCreator {

    public static CustomerPostRequestBody createPostRequestBodyCreator() {
        return new CustomerPostRequestBody(
            "Fulano da Silva",
            "fulano@gmail.com",
            LocalDate.of(2000, 1, 1)
        );
    }
    public static CustomerPostRequestBody createPostRequestBodyWithoutNameCreator() {
        return new CustomerPostRequestBody(
            "",
            "fulano@gmail.com",
            LocalDate.of(2000, 1, 1)
        );
    }
    public static CustomerPostRequestBody createPostRequestBodyWithInvalidNameCreator() {
        return new CustomerPostRequestBody(
            "jo",
            "fulano@gmail.com",
            LocalDate.of(2000, 1, 1)
        );
    }
}
