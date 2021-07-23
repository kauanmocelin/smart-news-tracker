package dev.kauanmocelin.springbootrestapi.customer;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Customer {
    private Long id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
}
