package dev.kauanmocelin.springbootrestapi.util;

import dev.kauanmocelin.springbootrestapi.authentication.registration.request.RegistrationRequest;

public class RegistrationPostRequestBodyCreator {

    public static RegistrationRequest createPostRequestBodyCreator() {
        return RegistrationRequest.builder()
            .firstName("Fulano")
            .lastName("da Silva")
            .email("fulano@gmail.com")
            .password("123456")
            .build();
    }

    public static RegistrationRequest createPostRequestBodyWithoutNameCreator() {
        return RegistrationRequest.builder()
            .firstName("")
            .lastName("da Silva")
            .email("fulano@gmail.com")
            .password("123456")
            .build();
    }

    public static RegistrationRequest createPostRequestBodyWithInvalidNameCreator() {
        return RegistrationRequest.builder()
            .firstName("fu")
            .lastName("da Silva")
            .email("fulano@gmail.com")
            .password("123456")
            .build();
    }
}
