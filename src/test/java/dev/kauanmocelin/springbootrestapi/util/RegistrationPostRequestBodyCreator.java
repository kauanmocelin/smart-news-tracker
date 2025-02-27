package dev.kauanmocelin.springbootrestapi.util;

import dev.kauanmocelin.springbootrestapi.authentication.registration.dto.RegistrationRequest;

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
            .firstName("Fu")
            .lastName("da Silva")
            .email("fulano@gmail.com")
            .password("123456")
            .build();
    }

    public static RegistrationRequest createPostRequestBodyWithInvalidEmailCreator() {
        return RegistrationRequest.builder()
            .firstName("Flano")
            .lastName("da Silva")
            .email("fulanogmail.com")
            .password("123456")
            .build();
    }
}
