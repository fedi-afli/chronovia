package com.Chronova.ChronovaStore.dataDTO;

import java.util.List;

public record CartRequestDTO(
        List<CartLignRequestDTO> cartLigns,
        Double total
) {

}
