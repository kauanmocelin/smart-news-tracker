package dev.kauanmocelin.springbootrestapi.customer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class CustomerPutRequestBody {
    @NotNull(message = "The customer id cannot be empty")
    @Schema(description = "This is the Customer's id", example = "1")
    private Long id;

    @NotBlank(message = "The customer name cannot be empty")
    @Size(min = 3, max = 100)
    @Schema(description = "This is the Customer's name", example = "Fulano da Silva")
    private String name;

    @NotBlank(message = "The customer e-mail cannot be empty")
    @Email
    @Schema(description = "This is the Customer's e-mail", example = "fulano@gmail.com")
    private String email;

    @PastOrPresent
    @Schema(description = "This is the Customer's date of birth", example = "2000-01-01")
    private LocalDate dateOfBirth;
}
