package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.dataDTO.WatchRequestDTO;
import com.Chronova.ChronovaStore.models.MechanicalWatch;
import com.Chronova.ChronovaStore.models.QuartzWatch;
import com.Chronova.ChronovaStore.models.Watch;
import com.Chronova.ChronovaStore.models.types.WatchType;
import com.Chronova.ChronovaStore.repository.WatchRepository;
import org.springframework.stereotype.Service;

@Service
public class WatchService {
    private WatchRepository watchRepository;

    public WatchService(WatchRepository watchRepository) {
        this.watchRepository = watchRepository;
    }


    public Watch watchRequest_to_watch(WatchRequestDTO dto) {
        if (dto.watchType() == WatchType.QUARTZ) {
            return new QuartzWatch(
                    dto.referenceNumber(),
                    dto.price(),
                    dto.modelName(),
                    dto.brandName(),
                    dto.modelDescription(),
                    dto.caseWidth(),
                    dto.caseHeight(),
                    dto.watchMaterial(),
                    dto.modelYear(),
                    dto.movementCaliber(),
                    dto.watchType(),
                    dto.batteryType(),
                    dto.isSolar(),
                    dto.accuracy()
            );
        } else {
            return new MechanicalWatch(
                    dto.referenceNumber(),
                    dto.price(),
                    dto.modelName(),
                    dto.brandName(),
                    dto.modelDescription(),
                    dto.caseWidth(),
                    dto.caseHeight(),
                    dto.watchMaterial(),
                    dto.modelYear(),
                    dto.movementCaliber(),
                    dto.watchType(),
                    dto.isSelfWind(),
                    dto.powerReserveHours(),
                    dto.jewelCount()
            );
        }
    }

    public WatchRequestDTO watchToWatchRequestDTO(Watch watch) {
        if (watch instanceof QuartzWatch quartzWatch) {
            return new WatchRequestDTO(
                    quartzWatch.getReferenceNumber(),
                    quartzWatch.getPrice(),
                    quartzWatch.getModelName(),
                    quartzWatch.getBrandName(),
                    quartzWatch.getModelDescription(),
                    quartzWatch.getCaseWidth(),
                    quartzWatch.getCaseheight(),
                    quartzWatch.getWatchMaterial(),
                    quartzWatch.getWatchType(),
                    quartzWatch.getModelYear(),
                    quartzWatch.getMovementCaliber(),
                    quartzWatch.getBatteryType(),
                    quartzWatch.isSolar(),
                    quartzWatch.getAccuracy(),
                    false,        // isSelfWind
                    null,         // powerReserveHours
                    null          // jewelCount
            );
        } else if (watch instanceof MechanicalWatch mechWatch) {
            return new WatchRequestDTO(
                    mechWatch.getReferenceNumber(),
                    mechWatch.getPrice(),
                    mechWatch.getModelName(),
                    mechWatch.getBrandName(),
                    mechWatch.getModelDescription(),
                    mechWatch.getCaseWidth(),
                    mechWatch.getCaseheight(),
                    mechWatch.getWatchMaterial(),
                    mechWatch.getWatchType(),
                    mechWatch.getModelYear(),
                    mechWatch.getMovementCaliber(),
                    null,          // batteryType
                    false,         // isSolar
                    null,          // accuracy
                    mechWatch.isSelfWind(),
                    mechWatch.getPowerReserveHours(),
                    mechWatch.getJewelCount()
            );
        } else {
            // fallback for base Watch or unknown subtype
            return new WatchRequestDTO(
                    watch.getReferenceNumber(),
                    watch.getPrice(),
                    watch.getModelName(),
                    watch.getBrandName(),
                    watch.getModelDescription(),
                    watch.getCaseWidth(),
                    watch.getCaseheight(),
                    watch.getWatchMaterial(),
                    watch.getWatchType(),
                    watch.getModelYear(),
                    watch.getMovementCaliber(),
                    null,   // batteryType
                    false,  // isSolar
                    null,   // accuracy
                    false,  // isSelfWind
                    null,   // powerReserveHours
                    null    // jewelCount
            );
        }
    }
}



