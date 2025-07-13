package com.Chronova.ChronovaStore.dataDTO;

import jakarta.validation.constraints.NotBlank;

public record SignInRequestDTO(
        @NotBlank(message = "Email or username is required")
        String emailOrUsername,
        
        @NotBlank(message = "Password is required")
        String password
) {}