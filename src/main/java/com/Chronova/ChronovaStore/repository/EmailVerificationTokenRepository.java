package com.Chronova.ChronovaStore.repository;

import com.Chronova.ChronovaStore.models.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Integer> {
    Optional<EmailVerificationToken> findByToken(String token);
    Optional<EmailVerificationToken> findByUserId(Integer userId);
    void deleteByExpiryDateBefore(LocalDateTime now);
    void deleteByUserId(Integer userId);
}