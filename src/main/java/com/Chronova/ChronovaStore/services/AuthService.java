package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.dataDTO.auth.AuthResponse;
import com.Chronova.ChronovaStore.dataDTO.auth.LoginRequest;
import com.Chronova.ChronovaStore.dataDTO.auth.RegisterRequest;
import com.Chronova.ChronovaStore.models.Cart;
import com.Chronova.ChronovaStore.models.EmailVerificationToken;
import com.Chronova.ChronovaStore.models.Role;
import com.Chronova.ChronovaStore.models.User;
import com.Chronova.ChronovaStore.repository.CartRepository;
import com.Chronova.ChronovaStore.repository.EmailVerificationTokenRepository;
import com.Chronova.ChronovaStore.repository.UserRepository;
import com.Chronova.ChronovaStore.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final EmailVerificationTokenRepository tokenRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public AuthService(UserRepository userRepository,
                      EmailVerificationTokenRepository tokenRepository,
                      CartRepository cartRepository,
                      PasswordEncoder passwordEncoder,
                      JwtService jwtService,
                      AuthenticationManager authenticationManager,
                      EmailService emailService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already registered");
        }
        
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Username already taken");
        }

        // Create new user
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(Role.USER);
        user.setEnabled(false); // Will be enabled after email verification

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
        emailService.sendVerificationEmail(savedUser.getEmail(), token);

        return new AuthResponse(
                null, // No JWT token until email is verified
                "Registration successful. Please check your email to verify your account.",
                savedUser.getEmail(),
                savedUser.getUsername(),
                false
        );
    }

    public AuthResponse login(LoginRequest request) {
        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.isEnabled()) {
            throw new RuntimeException("Please verify your email before logging in");
        }

        // Generate JWT token
        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(
                jwtToken,
                "Login successful",
                user.getEmail(),
                user.getUsername(),
                true
        );
    }

    @Transactional
    public AuthResponse verifyEmail(String token) {
        EmailVerificationToken verificationToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));

        if (verificationToken.isExpired()) {
            throw new RuntimeException("Verification token has expired");
        }

        if (verificationToken.isUsed()) {
            throw new RuntimeException("Verification token has already been used");
        }

        // Enable user account
        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        // Mark token as used
        verificationToken.setUsed(true);
        tokenRepository.save(verificationToken);

        // Generate JWT token for immediate login
        String jwtToken = jwtService.generateToken(user);

        return new AuthResponse(
                jwtToken,
                "Email verified successfully. You are now logged in.",
                user.getEmail(),
                user.getUsername(),
                true
        );
    }

    public void resendVerificationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.isEnabled()) {
            throw new RuntimeException("Email is already verified");
        }

        // Delete existing tokens for this user
        tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);

        // Generate new verification token
        String token = UUID.randomUUID().toString();
        EmailVerificationToken verificationToken = new EmailVerificationToken(token, user);
        tokenRepository.save(verificationToken);

        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), token);
    }
}