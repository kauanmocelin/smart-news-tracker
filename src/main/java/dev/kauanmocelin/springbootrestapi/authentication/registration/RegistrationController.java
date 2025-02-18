package dev.kauanmocelin.springbootrestapi.authentication.registration;

import dev.kauanmocelin.springbootrestapi.authentication.registration.request.LoginRequest;
import dev.kauanmocelin.springbootrestapi.authentication.registration.request.LoginResponse;
import dev.kauanmocelin.springbootrestapi.authentication.registration.request.RegistrationRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api/v1/auth")
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

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

    @Operation(summary = "User login", description = "User login", tags = {"authentication"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User logged in successfully"),
        @ApiResponse(responseCode = "403", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(registrationService.login(request));
    }
}
