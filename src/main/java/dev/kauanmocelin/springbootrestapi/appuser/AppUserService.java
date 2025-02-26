package dev.kauanmocelin.springbootrestapi.appuser;

import dev.kauanmocelin.springbootrestapi.appuser.mapper.AppUserMapper;
import dev.kauanmocelin.springbootrestapi.appuser.request.AppUserPutRequestBody;
import dev.kauanmocelin.springbootrestapi.appuser.response.AppUserResponseBody;
import dev.kauanmocelin.springbootrestapi.authentication.registration.code.RegistrationCode;
import dev.kauanmocelin.springbootrestapi.authentication.registration.code.RegistrationCodeService;
import dev.kauanmocelin.springbootrestapi.common.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final RegistrationCodeService registrationCodeService;
    private final PasswordEncoder passwordEncoder;
    private final AppUserMapper appUserMapper;

    public List<AppUserResponseBody> findAll() {
        return appUserRepository.findAll().stream()
            .map(appUserMapper::toAppUserResponseBody)
            .toList();
    }

    public AppUserResponseBody findById(final Long appUserId) {
        AppUser appUser = findByIdOrThrowBadRequestException(appUserId);
        return appUserMapper.toAppUserResponseBody(appUser);
    }

    public void replace(AppUserPutRequestBody appUserPutRequestBody) {
        AppUser savedCustomer = findByIdOrThrowBadRequestException(appUserPutRequestBody.getId());

        Optional<AppUser> appUserOptional = appUserRepository.findByEmail(appUserPutRequestBody.getEmail());
        if (appUserOptional.isPresent() && !appUserOptional.get().getId().equals(savedCustomer.getId())) {
            throw new BadRequestException("email taken");
        }
        BeanUtils.copyProperties(appUserPutRequestBody, savedCustomer, "id");
        appUserRepository.save(savedCustomer);
    }

    private AppUser findByIdOrThrowBadRequestException(final Long appUserId) {
        return appUserRepository.findById(appUserId)
            .orElseThrow(() -> new BadRequestException("user with id " + appUserId + " does not exists"));
    }

    public void delete(Long appUserId) {
        final var userExists = appUserRepository.existsById(appUserId);
        if (!userExists) {
            throw new BadRequestException("user with id " + appUserId + " does not exists");
        }
        appUserRepository.deleteById(appUserId);
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("user with email %s not found", email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail())
            .isPresent();
        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            throw new BadRequestException("email already taken");
        }
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        final var token = UUID.randomUUID().toString();
        RegistrationCode registrationCode = new RegistrationCode(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            appUser
        );
        registrationCodeService.saveConfirmationToken(registrationCode);
        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
