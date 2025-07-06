package com.Chronova.ChronovaStore.dataDTO;

public record UserRequestDTO(
                 String username,
                 String email,
                 String password,
                 CartRequestDTO cart
) {
}
