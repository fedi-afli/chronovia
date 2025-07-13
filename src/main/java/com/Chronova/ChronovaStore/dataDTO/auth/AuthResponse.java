package com.Chronova.ChronovaStore.dataDTO.auth;

public record AuthResponse(
        String token,
        String message,
        String email,
        String username,
        boolean authenticated
) {
}