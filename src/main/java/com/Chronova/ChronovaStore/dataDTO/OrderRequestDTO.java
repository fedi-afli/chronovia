package com.Chronova.ChronovaStore.dataDTO;

import java.time.LocalDateTime;

public record OrderRequestDTO(
       CartRequestDTO cart,
       LocalDateTime orderDate
) {
}
