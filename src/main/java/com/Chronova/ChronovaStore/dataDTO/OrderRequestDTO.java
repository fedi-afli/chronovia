package com.Chronova.ChronovaStore.dataDTO;

import java.time.LocalDateTime;

public record OrderRequestDTO(
       Integer userId,
       LocalDateTime orderDate,
       Double total
) {
}
