package com.Chronova.ChronovaStore.dataDTO;

import java.time.LocalDateTime;

public record UserRequestDTO(
                 Integer id,
                 String username,
                 String email,
                 String role,
                 boolean enabled,
                 LocalDateTime createdAt,
                 LocalDateTime lastLoginAt
) {
}
