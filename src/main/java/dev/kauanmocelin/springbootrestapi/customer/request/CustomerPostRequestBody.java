package dev.kauanmocelin.springbootrestapi.customer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class CustomerPostRequestBody {
    @NotBlank(message = "The customer name cannot be empty")
    @Size(min = 3, max = 100)
    @Schema(description = "This is the Customer's name", example = "Fulano da Silva")
    private String name;

    @NotBlank(message = "The customer e-mail cannot be empty")
    @Email
    @Schema(description = "This is the Customer's e-mail", example = "fulano@gmail.com")
    private String email;

    @Schema(description = "This is the Customer's date of birth", example = "2000-01-01")
    @PastOrPresent
    private LocalDate dateOfBirth;
}
