package dev.kauanmocelin.springbootrestapi.customer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Period;

@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The customer name cannot be empty")
    @Size(min = 3, max = 100)
    private String name;

    @NotBlank(message = "The customer e-mail cannot be empty")
    @Email
    private String email;

    @PastOrPresent
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
}

