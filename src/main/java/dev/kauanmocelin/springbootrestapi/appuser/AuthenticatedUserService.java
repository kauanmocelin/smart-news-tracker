package dev.kauanmocelin.springbootrestapi.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticatedUserService {

    private final AppUserRepository appUserRepository;

    public AppUser getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedUserEmail = authentication.getName();
        return appUserRepository.findByEmail(loggedUserEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
