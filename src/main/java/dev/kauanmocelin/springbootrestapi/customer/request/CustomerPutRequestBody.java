package dev.kauanmocelin.springbootrestapi.customer.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CustomerPutRequestBody {
    @NotBlank(message = "The customer name cannot be empty")
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank(message = "The customer e-mail cannot be empty")
    @Email
    private String email;

    @PastOrPresent
    private LocalDate dateOfBirth;
}
