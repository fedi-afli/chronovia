package com.Chronova.ChronovaStore.dataDTO;

import jakarta.validation.constraints.NotBlank;

public record EmailVerificationRequestDTO(
        @NotBlank(message = "Token is required")
        String token
) {}