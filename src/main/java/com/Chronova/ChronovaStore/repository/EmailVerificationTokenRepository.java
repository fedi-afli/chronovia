package com.Chronova.ChronovaStore.repository;

import com.Chronova.ChronovaStore.models.EmailVerificationToken;
import com.Chronova.ChronovaStore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Integer> {
    Optional<EmailVerificationToken> findByToken(String token);
    Optional<EmailVerificationToken> findByUser(User user);
    void deleteByExpiryDateBefore(LocalDateTime dateTime);
    List<EmailVerificationToken> findByExpiryDateBefore(LocalDateTime dateTime);
}