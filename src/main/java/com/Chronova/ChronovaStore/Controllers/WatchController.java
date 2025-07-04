package com.Chronova.ChronovaStore.Controllers;

import com.Chronova.ChronovaStore.dataDTO.WatchRequestDTO;
import com.Chronova.ChronovaStore.models.Watch;
import com.Chronova.ChronovaStore.repository.WatchRepository;
import com.Chronova.ChronovaStore.services.WatchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class WatchController {
    private final WatchService watchService;
    private final WatchRepository watchRepository;

    public WatchController(WatchService watchService, WatchRepository watchRepository) {
        this.watchService = watchService;
        this.watchRepository = watchRepository;
    }

    @PostMapping("/save/watch")
    public WatchRequestDTO saveWatch(@RequestBody WatchRequestDTO watchRequestDTO) {
        Watch watch = watchService.watchRequest_to_watch(watchRequestDTO);
        Watch savedWatch = watchRepository.save(watch);
        return watchService.watchToWatchRequestDTO(savedWatch);
    }



}
