package dev.kauanmocelin.springbootrestapi.registration;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class RegistrationRequest {

    private final String firstName;
    private final String lastName;
    private final String password;
    private final String email;
}
