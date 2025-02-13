package dev.kauanmocelin.springbootrestapi.authentication.registration;

import dev.kauanmocelin.springbootrestapi.authentication.registration.request.LoginRequest;
import dev.kauanmocelin.springbootrestapi.authentication.registration.request.LoginResponse;
import dev.kauanmocelin.springbootrestapi.authentication.registration.request.RegistrationRequest;
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
    public ResponseEntity<String> register(@RequestBody RegistrationRequest request) {
        final var registrationCode = registrationService.register(request);
        final var uri =  ServletUriComponentsBuilder
            .fromCurrentContextPath()
            .replacePath("/api/v1/auth/verify")
            .queryParam("token", registrationCode)
            .build()
            .toUri();
        return ResponseEntity.created(uri).body(registrationCode);
    }

    @GetMapping(path = "/verify")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(registrationService.login(request));
    }

    //    @PostMapping
//    @Operation(summary = "Register new customer", description = "Register new customer", tags = {"customer"})
//    public ResponseEntity<Customer> registerNewCustomer(@RequestBody @Valid CustomerPostRequestBody customerPostRequestBody) {
//        return new ResponseEntity<>(appUserService.save(customerPostRequestBody), HttpStatus.CREATED);
//    }
}
