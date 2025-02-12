package dev.kauanmocelin.springbootrestapi.authentication.registration.request;

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


//    @NotBlank(message = "The user name cannot be empty")
//    @Size(min = 3, max = 100, message = "The customer name must be between 3 and 100 characters")
//    @Schema(description = "This is the Customer's name", example = "Fulano da Silva")
//    String name,
//
//    @NotBlank(message = "The customer e-mail cannot be empty")
//    @Email(message = "The customer email should be valid")
//    @Schema(description = "This is the Customer's e-mail", example = "fulano@gmail.com")
//    String email,
//
//    @Schema(description = "This is the Customer's date of birth", example = "2000-01-01")
//    @NotNull(message = "The customer date of birth cannot be null")
//    @PastOrPresent(message = "The customer date of birth must be in past or present")
//    LocalDate dateOfBirth
}
