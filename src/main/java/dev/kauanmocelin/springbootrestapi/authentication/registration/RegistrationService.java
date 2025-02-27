package dev.kauanmocelin.springbootrestapi.authentication.registration;

import dev.kauanmocelin.springbootrestapi.appuser.AppUser;
import dev.kauanmocelin.springbootrestapi.appuser.AppUserRepository;
import dev.kauanmocelin.springbootrestapi.appuser.AppUserService;
import dev.kauanmocelin.springbootrestapi.appuser.role.RoleRepository;
import dev.kauanmocelin.springbootrestapi.appuser.role.RoleType;
import dev.kauanmocelin.springbootrestapi.authentication.registration.code.RegistrationCode;
import dev.kauanmocelin.springbootrestapi.authentication.registration.code.RegistrationCodeService;
import dev.kauanmocelin.springbootrestapi.authentication.registration.dto.LoginRequest;
import dev.kauanmocelin.springbootrestapi.authentication.registration.dto.LoginResponse;
import dev.kauanmocelin.springbootrestapi.authentication.registration.dto.RegistrationRequest;
import dev.kauanmocelin.springbootrestapi.authentication.token.Token;
import dev.kauanmocelin.springbootrestapi.authentication.token.TokenRepository;
import dev.kauanmocelin.springbootrestapi.authentication.token.TokenType;
import dev.kauanmocelin.springbootrestapi.common.security.JwtService;
import dev.kauanmocelin.springbootrestapi.email.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final AppUserService appUserService;
    private final RegistrationCodeService registrationCodeService;
    private final EmailSender emailSender;
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository appUserRepository;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final RoleRepository roleRepository;
    private final TemplateEngine templateEngine;

    @Value("${application.base-url}")
    private String baseUrl;

    public String register(RegistrationRequest request) {
        final var token = appUserService.signUpUser(
            AppUser.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .locked(false)
                .enabled(false)
                .roles(Collections.singletonList(roleRepository.findByType(RoleType.USER)))
                .build()
        );
        final var link = UriComponentsBuilder.fromUriString(baseUrl)
            .path("/api/v1/auth/verify")
            .queryParam("token", token)
            .toUriString();
        emailSender.send(
            request.getEmail(),
            buildEmail(request.getFirstName(), link),
            "Confirm your email");
        return token;
    }

    @Transactional
    public void confirmToken(String token) {
        RegistrationCode registrationCode = registrationCodeService
            .getToken(token)
            .orElseThrow(() ->
                new IllegalStateException("token not found"));

        if (registrationCode.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = registrationCode.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        registrationCodeService.setConfirmedAt(token);
        appUserService.enableAppUser(
            registrationCode.getAppUser().getEmail());
    }

    private String buildEmail(final String name, final String link) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("link", link);
        return templateEngine.process("confirmation-account-email", context);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = appUserRepository.findByEmail(request.getUsername()).orElseThrow();
        final var jwtToken = jwtService.generateToken(user);
        final var refreshJwtToken = jwtService.generateRefreshToken(user);
        //revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return LoginResponse.builder()
            .accessToken(jwtToken)
            .refreshToken(refreshJwtToken)
            .build();
    }

    private void saveUserToken(AppUser appUser, String jwtToken) {
        Token token = Token.builder()
            .appUser(appUser)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .revoked(false)
            .expired(false).build();
        tokenRepository.save(token);
    }
}
