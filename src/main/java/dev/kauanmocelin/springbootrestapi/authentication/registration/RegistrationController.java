package dev.kauanmocelin.springbootrestapi.authentication.registration;

import dev.kauanmocelin.springbootrestapi.authentication.registration.dto.LoginRequest;
import dev.kauanmocelin.springbootrestapi.authentication.registration.dto.LoginResponse;
import dev.kauanmocelin.springbootrestapi.authentication.registration.dto.RegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/v1/auth")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final LogoutHandler logoutHandler;

    @SecurityRequirements
    @PostMapping("/register")
    @Operation(summary = "Register new user", description = "Register new user", tags = {"authentication"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User successfully registered. A verification email has been sent."),
        @ApiResponse(responseCode = "400", description = "User email already has been used")
    })
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationRequest request) {
        final var registrationCode = registrationService.register(request);
        final var uri = ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .replacePath("/api/v1/auth/verify")
            .queryParam("token", registrationCode)
            .build()
            .toUri();
        return ResponseEntity.created(uri).body(registrationCode);
    }

    @SecurityRequirements
    @Operation(summary = "Verify confirmation code", description = "Verify confirmation codeVerify confirmation code", tags = {"authentication"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Confirmation code successfully verified"),
        @ApiResponse(responseCode = "400", description = "Possible reasons: confirmation code not found, account already confirmed or confirmation code expired"),
    })
    @GetMapping(path = "/verify")
    public String confirm(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
        return "Your account has confirmed successfully";
    }

    @SecurityRequirements
    @Operation(summary = "User login", description = "User login", tags = {"authentication"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged in successfully"),
        @ApiResponse(responseCode = "403", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(registrationService.login(request));
    }

    @Operation(summary = "User logout", description = "User logout", tags = {"authentication"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged out successfully"),
        @ApiResponse(responseCode = "403", description = "Invalid credentials")
    })
    @PostMapping("/logout")
    public ResponseEntity<Void> authenticate(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutHandler.logout(request, response, authentication);
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }
}
