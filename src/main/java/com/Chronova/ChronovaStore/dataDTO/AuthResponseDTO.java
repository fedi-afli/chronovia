package com.Chronova.ChronovaStore.dataDTO;

public record AuthResponseDTO(
        String token,
        String type,
        Integer userId,
        String username,
        String email,
        String role,
        boolean emailVerified
) {
    public AuthResponseDTO(String token, Integer userId, String username, String email, String role, boolean emailVerified) {
        this(token, "Bearer", userId, username, email, role, emailVerified);
    }
}