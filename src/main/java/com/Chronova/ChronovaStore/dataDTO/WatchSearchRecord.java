package com.Chronova.ChronovaStore.dataDTO;

import com.Chronova.ChronovaStore.models.types.WatchMaterial;
import com.Chronova.ChronovaStore.models.types.WatchType;

public record WatchSearchRecord(String referenceNumber,
                                Double minPrice,
                                Double MaxPrice,
                                String modelName,
                                String brandName,
                                Integer caseWidth,
                                WatchMaterial watchMaterial,
                                WatchType watchType,
                                Integer modelYear,
                                String movementCaliber,
                                String batteryType,
                                Boolean isSolar,
                                Integer accuracy,
                                Boolean isSelfWind,
                                Integer powerReserveHours,
                                Integer jewelCount) {
}
