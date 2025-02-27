package dev.kauanmocelin.springbootrestapi.authentication.registration.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class RegistrationRequest {

    @NotBlank(message = "The user first name cannot be empty")
    @Size(min = 3, max = 100, message = "The user first name must be between 3 and 100 characters")
    @Schema(description = "This is the User's first name", example = "Fulano")
    private final String firstName;

    @NotBlank(message = "The user last name cannot be empty")
    @Size(min = 3, max = 100, message = "The user last name must be between 3 and 100 characters")
    @Schema(description = "This is the User's last name", example = "da Silva")
    private final String lastName;

    @NotBlank(message = "The user password cannot be empty")
    @Schema(description = "This is the User's password", example = "")
    private final String password;

    @NotBlank(message = "The user e-mail cannot be empty")
    @Email(message = "The user email should be valid")
    @Schema(description = "This is the User's e-mail", example = "fulano@gmail.com")
    private final String email;
}
