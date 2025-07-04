package com.Chronova.ChronovaStore.dataDTO;

import com.Chronova.ChronovaStore.models.types.WatchMaterial;
import com.Chronova.ChronovaStore.models.types.WatchType;

public record WatchRequestDTO(
        String referenceNumber,
        double price,
        String modelName,
        String brandName,
        String modelDescription,
        Long caseWidth,
        Long caseHeight,
        WatchMaterial watchMaterial,
        WatchType watchType,
        Integer modelYear,
        String movementCaliber,

        // Fields specific to Quartz watches
        String batteryType,
        boolean isSolar,
        Integer accuracy,

        // Fields specific to Mechanical watches
        boolean isSelfWind,
        Integer powerReserveHours,
        Integer jewelCount
) {
}
