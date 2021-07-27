package dev.kauanmocelin.springbootrestapi.customer.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CustomerPostRequestBody {
    private String name;
    private String email;
    private LocalDate dateOfBirth;
}
