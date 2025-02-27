package dev.kauanmocelin.springbootrestapi.appuser.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class AppUserPutRequestBody {

    @NotNull(message = "The user id cannot be empty")
    @Schema(description = "This is the User's id", example = "1")
    private Long id;

    @NotBlank(message = "The user first name cannot be empty")
    @Size(min = 3, max = 100)
    @Schema(description = "This is the User's first name", example = "Fulano")
    private String firstName;

    @NotBlank(message = "The user last name cannot be empty")
    @Size(min = 3, max = 100)
    @Schema(description = "This is the User's last name", example = "da Silva")
    private String lastName;

    @NotBlank(message = "The user e-mail cannot be empty")
    @Email
    @Schema(description = "This is the User's e-mail", example = "fulano@gmail.com")
    private String email;

    @PastOrPresent
    @Schema(description = "This is the User's date of birth", example = "2000-01-01")
    private LocalDate dateOfBirth;

    private Boolean locked;

    private Boolean enabled;
}
