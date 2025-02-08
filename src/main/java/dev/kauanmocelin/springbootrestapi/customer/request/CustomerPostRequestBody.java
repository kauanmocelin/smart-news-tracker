package dev.kauanmocelin.springbootrestapi.customer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CustomerPostRequestBody(
    @NotBlank(message = "The customer name cannot be empty")
    @Size(min = 3, max = 100, message = "The customer name must be between 3 and 100 characters")
    @Schema(description = "This is the Customer's name", example = "Fulano da Silva")
    String name,

    @NotBlank(message = "The customer e-mail cannot be empty")
    @Email(message = "The customer email should be valid")
    @Schema(description = "This is the Customer's e-mail", example = "fulano@gmail.com")
    String email,

    @Schema(description = "This is the Customer's date of birth", example = "2000-01-01")
    @NotNull(message = "The customer date of birth cannot be null")
    @PastOrPresent(message = "The customer date of birth must be in past or present")
    LocalDate dateOfBirth
    ) {}
