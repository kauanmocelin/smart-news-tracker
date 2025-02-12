package dev.kauanmocelin.springbootrestapi.appuser;

import dev.kauanmocelin.springbootrestapi.appuser.role.Role;
import dev.kauanmocelin.springbootrestapi.authentication.token.Token;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;

@Data
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "app_users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "The user first name cannot be empty")
    @Size(min = 3, max = 100, message = "The user first name must be between 3 and 100 characters")
    private String firstName;

    @NotBlank(message = "The user last name cannot be empty")
    @Size(min = 3, max = 100, message = "The user last name must be between 3 and 100 characters")
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "The user e-mail cannot be empty")
    @Email(message = "The user email should be valid")
    private String email;

    @NotBlank(message = "The user password cannot be empty")
    private String password;

    @PastOrPresent(message = "The user date of birth must be in past or present")
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    private Boolean locked;

    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();

    @OneToMany(mappedBy = "appUser")
    private Collection<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }
}