package com.Chronova.ChronovaStore.Controllers;

import com.Chronova.ChronovaStore.dataDTO.WatchDTO;
import com.Chronova.ChronovaStore.models.Specification;
import com.Chronova.ChronovaStore.models.SpecificationMechanical;
import com.Chronova.ChronovaStore.models.SpecificationQuartz;
import com.Chronova.ChronovaStore.models.Watch;
import com.Chronova.ChronovaStore.models.types.WatchType;
import com.Chronova.ChronovaStore.repository.SpecificationRepository;
import com.Chronova.ChronovaStore.repository.WatchRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/watches")
public class WacthController {
    WatchRepository watchRepository;
    SpecificationRepository specificationRepository;

    public WacthController(WatchRepository watchRepository, SpecificationRepository specificationRepository) {
        this.watchRepository = watchRepository;
        this.specificationRepository = specificationRepository;
    }

    @PostMapping("post/watch")
    public Watch addWatch(@RequestBody WatchDTO watchDTO){
        // Create the appropriate Specification based on the watchType
        Specification specification;

        if (watchDTO.getWatchType() == WatchType.QUARTZ) {
            SpecificationQuartz quartz = new SpecificationQuartz(
                    watchDTO.getModelName(),
                    watchDTO.getModelDescription(),
                    watchDTO.getCaseHeight(),
                    watchDTO.getCaseWidth(),
                    watchDTO.getModelYear(),
                    watchDTO.getWatchMaterial(),
                    watchDTO.getMovementCaliber(),
                    watchDTO.getBrandName(),
                    watchDTO.getWatchType(),
                    watchDTO.getBatteryType(),
                    watchDTO.isSolar(),
                    watchDTO.getAccuracy()
            );
            specification = quartz;
        } else if (watchDTO.getWatchType() == WatchType.MECHANICAL) {
            SpecificationMechanical mech = new SpecificationMechanical(
                    watchDTO.getModelName(),
                    watchDTO.getModelDescription(),
                    watchDTO.getCaseHeight(),
                    watchDTO.getCaseWidth(),
                    watchDTO.getModelYear(),
                    watchDTO.getWatchMaterial(),
                    watchDTO.getMovementCaliber(),
                    watchDTO.getBrandName(),
                    watchDTO.getWatchType(),
                    watchDTO.isSelfWind(),
                    watchDTO.getPowerReserveHours(),
                    watchDTO.getJewelCount()
            );
            specification = mech;
        } else {
            throw new IllegalArgumentException("Unsupported watch type: " + watchDTO.getWatchType());
        }

        // Save specification first (assuming you have a SpecificationRepository)
        specificationRepository.save(specification);

        // Now create the Watch entity
        Watch watch = new Watch();
        watch.setReferenceNumber(watchDTO.getReferenceNumber());
        watch.setPrice(watchDTO.getPrice());
        watch.setSpecification(specification); // Set the specification

        // Save the Watch
        return watchRepository.save(watch);

    }
    @GetMapping("get/watch")
    public List<Watch> getWatchs(){
        return watchRepository.findAll();
    }


    @GetMapping("get/search_id/{watch_id}")
    public Watch getWatch(@PathVariable("watch_id") Integer watchId) {
        return watchRepository.findById(watchId).orElse(null);
    }
    @GetMapping("get/search_ref/{watch_ref}")
    public Watch getWatchByRef(@PathVariable("watch_ref") String watchref) {
        return watchRepository.findByReferenceNumber(watchref)  ;
    }
    @GetMapping("get/search_brand/{brandName}")
    public List<Watch> getWatchByBrandName(@PathVariable("brandName") String brandName) {
        return watchRepository.findBySpecification_BrandNameIgnoreCase(brandName)  ;
    }
    @PatchMapping("/patch/{id}")
    public ResponseEntity<Watch> partialUpdate(@PathVariable("id") Integer id, @RequestBody Map<String, Object> updates) {
        return watchRepository.findById(id)
                .map(watch -> {
                        if (updates.containsKey("price")) {
                            watch.setPrice((Double) updates.get("price"));
                        }
                        if (updates.containsKey("referenceNumber")) {
                            watch.setReferenceNumber((String) updates.get("referenceNumber"));
                        }
                    return ResponseEntity.ok(watchRepository.save(watch));
                })
                .orElse(ResponseEntity.notFound().build());
    }



}
