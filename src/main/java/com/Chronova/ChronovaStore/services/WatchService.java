package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.dataDTO.WatchRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.WatchSearchRecord;
import com.Chronova.ChronovaStore.models.*;
import com.Chronova.ChronovaStore.models.types.WatchType;
import com.Chronova.ChronovaStore.repository.PictureRepository;
import com.Chronova.ChronovaStore.repository.WatchRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WatchService {
    private final WatchRepository watchRepository;
    private final  PictureRepository  pictureRepository;


    public WatchService(WatchRepository watchRepository, PictureRepository pictureRepository) {
        this.watchRepository = watchRepository;
        this.pictureRepository = pictureRepository;
    }


    public Watch save(WatchRequestDTO watchRequestDTO) {
        Watch watch = watchRequest_to_watch(watchRequestDTO);
        Watch savedWatch = watchRepository.save(watch);
        return savedWatch;
    }


    public List<Watch> getAllWatches() {
        return watchRepository.findAll();

    }


    public Watch getWatchById(Integer watch_id ) {

        return watchRepository.findById(watch_id).orElse(null);

    }


    public Boolean deleteWatch(Integer watch_id){
        watchRepository.deleteById(watch_id);
        return true;
    }

    public List<WatchRequestDTO> searchWatches(WatchSearchRecord filter) {
        Specification<Watch> spec = WatchSpecifications.withAdvancedFilters(
                filter.referenceNumber(),
                filter.minPrice(),
                filter.MaxPrice(),
                filter.modelName(),
                filter.brandName(),
                filter.caseWidth(),        // Can be used for both min and max, or split later
                filter.caseWidth(),
                filter.watchMaterial(),
                filter.watchType(),
                filter.modelYear(),
                filter.movementCaliber(),
                filter.batteryType(),
                filter.isSolar(),
                filter.isSelfWind(),
                filter.jewelCount()
        );

        return watchRepository.findAll(spec).stream()
                .map(this::watchToWatchRequestDTO)
                .collect(Collectors.toList());
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
                    null// jewelCount
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
                    null// jewelCount

            );
        }
    }
}



