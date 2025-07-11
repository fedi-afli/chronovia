package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.WatchRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.WatchSearchRecord;
import com.Chronova.ChronovaStore.repository.WatchRepository;
import com.Chronova.ChronovaStore.services.WatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;




@RestController
@RequestMapping("api")
public class WatchController {
    private final WatchService watchService;


    public WatchController(WatchService watchService, WatchRepository watchRepository) {
        this.watchService = watchService;

    }

    @PostMapping("/save/watch")
    public WatchRequestDTO saveWatch(@RequestBody WatchRequestDTO watchRequestDTO) {
        return watchService.save(watchRequestDTO);
    }

    @PostMapping("/search/watch")
    public List<WatchRequestDTO> searchWatches(@RequestBody WatchSearchRecord filter) {
        return watchService.searchWatches(filter);
    }

    @GetMapping("/get/watch")
    public List<WatchRequestDTO> getWatch() {
        return  watchService.getAllWatches();

    }
    @GetMapping("/get/watch/{watch_id}")
    public WatchRequestDTO   getWatch(@RequestParam Integer watch_id ) {
        return watchService.getWatchById(watch_id);

    }
    @DeleteMapping("/delete/watch/{watch_id}")
    public Boolean deleteWatch(@PathVariable Integer watch_id) {
       return   watchService.deleteWatch(watch_id);
    }





}
