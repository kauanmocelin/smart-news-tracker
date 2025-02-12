package dev.kauanmocelin.springbootrestapi.authentication.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    @Query("""
        SELECT token FROM Token token INNER JOIN AppUser appUser ON token.appUser.id = appUser.id
        WHERE appUser.id = :userId AND (token.expired = false OR token.revoked = false)
        """)
    List<Token> findAllValidTokensByUsers(final Long userId);

    Optional<Token> findByToken(String token);
}
