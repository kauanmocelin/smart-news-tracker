package dev.kauanmocelin.springbootrestapi.appuser.dto;

import dev.kauanmocelin.springbootrestapi.appuser.role.RoleType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class AppUserResponseBody {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private Boolean locked;
    private Boolean enabled;
    private List<RoleType> roles;
}
