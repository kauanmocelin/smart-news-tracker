package dev.kauanmocelin.springbootrestapi.util;

import dev.kauanmocelin.springbootrestapi.appuser.dto.AppUserPutRequestBody;

public class CustomerPutRequestBodyCreator {

    public static AppUserPutRequestBody createPutRequestBodyCreator() {
        return AppUserPutRequestBody.builder()
            .id(AppUserCreator.createValidUpdatedAppUser().getId())
            .firstName(AppUserCreator.createValidUpdatedAppUser().getFirstName())
            .lastName(AppUserCreator.createValidUpdatedAppUser().getLastName())
            .email(AppUserCreator.createValidUpdatedAppUser().getEmail())
            .dateOfBirth(AppUserCreator.createValidUpdatedAppUser().getDateOfBirth())
            .build();
    }
}
