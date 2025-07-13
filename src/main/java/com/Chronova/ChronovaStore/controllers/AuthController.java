package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.*;
import com.Chronova.ChronovaStore.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MessageResponseDTO> registerUser(@Valid @RequestBody SignUpRequestDTO signUpRequest) {
        MessageResponseDTO response = authService.signUp(signUpRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDTO> authenticateUser(@Valid @RequestBody SignInRequestDTO signInRequest) {
        AuthResponseDTO response = authService.signIn(signInRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<MessageResponseDTO> verifyEmail(@RequestParam String token) {
        MessageResponseDTO response = authService.verifyEmail(token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<MessageResponseDTO> verifyEmailPost(@Valid @RequestBody EmailVerificationRequestDTO request) {
        MessageResponseDTO response = authService.verifyEmail(request.token());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-verification")
    public ResponseEntity<MessageResponseDTO> resendVerificationEmail(@RequestParam String email) {
        MessageResponseDTO response = authService.resendVerificationEmail(email);
        return ResponseEntity.ok(response);
    }
}