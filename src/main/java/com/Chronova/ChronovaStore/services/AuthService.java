package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.dataDTO.*;
import com.Chronova.ChronovaStore.models.Cart;
import com.Chronova.ChronovaStore.models.EmailVerificationToken;
import com.Chronova.ChronovaStore.models.PasswordResetToken;
import com.Chronova.ChronovaStore.models.User;
import com.Chronova.ChronovaStore.repository.CartRepository;
import com.Chronova.ChronovaStore.repository.EmailVerificationTokenRepository;
import com.Chronova.ChronovaStore.repository.PasswordResetTokenRepository;
import com.Chronova.ChronovaStore.repository.UserRepository;
import com.Chronova.ChronovaStore.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private EmailVerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    public MessageResponseDTO signUp(SignUpRequestDTO signUpRequest) {
        // Check if username exists
        if (userRepository.existsByUsername(signUpRequest.username())) {
            return new MessageResponseDTO("Username is already taken!", false);
        }

        // Check if email exists
        if (userRepository.existsByEmail(signUpRequest.email())) {
            return new MessageResponseDTO("Email is already in use!", false);
        }

        // Create new user
        User user = new User();
        user.setUsername(signUpRequest.username());
        user.setEmail(signUpRequest.email());
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));
        user.setEnabled(false); // Will be enabled after email verification
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        // Create cart for user
        Cart cart = new Cart();
        cart.setUser(savedUser);
        cart.setTotal(0.0);
        cart.setCartLigns(List.of());
        cartRepository.save(cart);

        // Generate verification token
        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken(token, savedUser);
        tokenRepository.save(verificationToken);

        // Send verification email
        try {
            emailService.sendVerificationEmail(savedUser.getEmail(), token);
            return new MessageResponseDTO("User registered successfully! Please check your email to verify your account.", true);
        } catch (Exception e) {
            return new MessageResponseDTO("User registered but failed to send verification email. Please contact support.", false);
        }
    }

    public AuthResponseDTO signIn(SignInRequestDTO signInRequest) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.emailOrUsername(),
                        signInRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();

        // Update last login
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        // Generate JWT token
        String jwt = jwtUtils.generateToken(user);

        return new AuthResponseDTO(
                jwt,"",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.isEnabled()
        );
    }

    public MessageResponseDTO verifyEmail(String token) {
        EmailVerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElse(null);

        if (verificationToken == null) {
            return new MessageResponseDTO("Invalid verification token!", false);
        }

        if (verificationToken.isExpired()) {
            tokenRepository.delete(verificationToken);
            return new MessageResponseDTO("Verification token has expired!", false);
        }

        if (verificationToken.isUsed()) {
            return new MessageResponseDTO("Verification token has already been used!", false);
        }

        // Enable user account
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        // Mark token as used
        verificationToken.setUsed(true);
        tokenRepository.save(verificationToken);

        return new MessageResponseDTO("Email verified successfully! You can now sign in.", true);
    }

    public MessageResponseDTO resendVerificationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return new MessageResponseDTO("User not found!", false);
        }

        if (user.isEnabled()) {
            return new MessageResponseDTO("Email is already verified!", false);
        }

        // Delete existing tokens for this user
        tokenRepository.deleteByUserId(user.getId());

        // Generate new verification token
        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken(token, user);
        tokenRepository.save(verificationToken);

        // Send verification email
        try {
            emailService.sendVerificationEmail(user.getEmail(), token);
            return new MessageResponseDTO("Verification email sent successfully!", true);
        } catch (Exception e) {
            return new MessageResponseDTO("Failed to send verification email. Please try again later.", false);
        }
    }

    public void cleanupExpiredTokens() {
        tokenRepository.deleteByExpiryDateBefore(LocalDateTime.now());
    }

    public MessageResponseDTO requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return new MessageResponseDTO("User with this email not found!", false);
        }

        // Delete existing tokens for this user (optional)
        passwordResetTokenRepository.deleteByUserId(user.getId());

        // Generate token valid for 1 hour
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(1);
        PasswordResetToken resetToken = new PasswordResetToken(token, user, expiryDate);
        passwordResetTokenRepository.save(resetToken);

        try {
            emailService.sendPasswordResetEmail(user.getEmail(), token);
            return new MessageResponseDTO("Password reset email sent successfully!", true);
        } catch (Exception e) {
            return new MessageResponseDTO("Failed to send password reset email.", false);
        }
    }

    public MessageResponseDTO resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token).orElse(null);

        if (resetToken == null) {
            return new MessageResponseDTO("Invalid password reset token!", false);
        }

        if (resetToken.isExpired()) {
            passwordResetTokenRepository.delete(resetToken);
            return new MessageResponseDTO("Password reset token has expired!", false);
        }

        if (resetToken.isUsed()) {
            return new MessageResponseDTO("Password reset token has already been used!", false);
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        return new MessageResponseDTO("Password has been reset successfully!", true);
    }
}