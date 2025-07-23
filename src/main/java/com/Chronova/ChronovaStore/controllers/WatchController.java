package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.WatchRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.WatchSearchRecord;
import com.Chronova.ChronovaStore.models.Picture;
import com.Chronova.ChronovaStore.models.Watch;
import com.Chronova.ChronovaStore.repository.PictureRepository;
import com.Chronova.ChronovaStore.repository.WatchRepository;
import com.Chronova.ChronovaStore.services.PictureService;
import com.Chronova.ChronovaStore.services.WatchService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class WatchController {
    private final WatchService watchService;



    public WatchController(WatchService watchService) {
        this.watchService = watchService;

    }


    @PostMapping("/save/watch")
    public WatchRequestDTO saveWatch(
            @RequestBody WatchRequestDTO watchRequestDTO){
        return watchService.watchToWatchRequestDTO( watchService.save(watchRequestDTO));

    }
    @PostMapping("/search/watch")
    public List<WatchRequestDTO> searchWatches(@RequestBody WatchSearchRecord filter) {
        return watchService.searchWatches(filter);
    }

    @GetMapping("/get/watch")
    public List<WatchRequestDTO> getWatch() {
        try {
            List<WatchRequestDTO> l = watchService.getAllWatches()
                    .stream()
                    .map(watchService::watchToWatchRequestDTO)
                    .collect(Collectors.toList());


            return l;
        } catch (Exception e) {
            e.printStackTrace(); // log full stack trace
            throw e; // rethrow to preserve the 500 error in frontend
        }
    }
    @GetMapping("/search/watch/{watch_id}")
    public WatchRequestDTO   getWatch(@PathVariable Integer watch_id ) {

        return watchService.watchToWatchRequestDTO(  watchService.getWatchById(watch_id));


    }
    @DeleteMapping("/delete/watch/{watch_id}")
    public Boolean deleteWatch(@PathVariable Integer watch_id) {
       return   watchService.deleteWatch(watch_id);
    }







}
