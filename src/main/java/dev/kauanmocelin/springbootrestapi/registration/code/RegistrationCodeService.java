package dev.kauanmocelin.springbootrestapi.registration.code;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationCodeService {

    private final RegistrationCodeRepository registrationCodeRepository;

    public void saveConfirmationToken(RegistrationCode token){
        registrationCodeRepository.save(token);
    }

    public Optional<RegistrationCode> getToken(String token) {
        return registrationCodeRepository.findByToken(token);
    }

    public int setConfirmedAt(String token) {
        return registrationCodeRepository.updateConfirmedAt(
            token, LocalDateTime.now());
    }
}
