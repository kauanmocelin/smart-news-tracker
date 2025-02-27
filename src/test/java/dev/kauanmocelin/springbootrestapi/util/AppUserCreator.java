package dev.kauanmocelin.springbootrestapi.util;

import dev.kauanmocelin.springbootrestapi.appuser.AppUser;
import dev.kauanmocelin.springbootrestapi.appuser.dto.AppUserResponseBody;

import java.time.LocalDate;

public class AppUserCreator {

    public static AppUser createAppUserToBeSaved() {
        return AppUser.builder()
            .firstName("Fulano")
            .lastName("da Silva")
            .email("fulano@gmail.com")
            .password("123456")
            .dateOfBirth(LocalDate.of(2000, 1, 1))
            .build();
    }

    public static AppUserResponseBody createValidAppUser() {
        return AppUserResponseBody.builder()
            .id(1L)
            .firstName("Fulano")
            .lastName("da Silva")
            .email("fulano@gmail.com")
            .dateOfBirth(LocalDate.of(2000, 1, 1))
            .build();
    }

    public static AppUser createValidUpdatedAppUser() {
        return AppUser.builder()
            .id(1L)
            .firstName("Fulano")
            .lastName("da Silva")
            .email("ciclano@gmail.com")
            .password("123456")
            .dateOfBirth(LocalDate.of(1995, 12, 1))
            .build();
    }
}
