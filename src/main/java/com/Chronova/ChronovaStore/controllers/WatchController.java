package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.WatchRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.WatchSearchRecord;
import com.Chronova.ChronovaStore.models.Picture;
import com.Chronova.ChronovaStore.repository.PictureRepository;
import com.Chronova.ChronovaStore.repository.WatchRepository;
import com.Chronova.ChronovaStore.services.PictureService;
import com.Chronova.ChronovaStore.services.WatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("api")
public class WatchController {
    private final WatchService watchService;
    private final PictureService pictureService;


    public WatchController(WatchService watchService, WatchRepository watchRepository, PictureService pictureService) {
        this.watchService = watchService;
        this.pictureService = pictureService;
    }

    @PostMapping("/save/watch")
    public WatchRequestDTO saveWatch(@RequestBody WatchRequestDTO watchRequestDTO) {
        return watchService.watchToWatchRequestDTO( watchService.save(watchRequestDTO));
    }
    @PostMapping("/search/watch")
    public List<WatchRequestDTO> searchWatches(@RequestBody WatchSearchRecord filter) {
        return watchService.searchWatches(filter);
    }

    @GetMapping("/get/watch")
    public List<WatchRequestDTO> getWatch() {
        return watchService.getAllWatches().stream().map(watchService::watchToWatchRequestDTO).collect(Collectors.toList());

    }
    @GetMapping("/get/watch/{watch_id}")
    public WatchRequestDTO   getWatchById(@RequestParam Integer watch_id ) {
        return watchService.watchToWatchRequestDTO(  watchService.getWatchById(watch_id));

    }
    @DeleteMapping("/delete/watch/{watch_id}")
    public Boolean deleteWatch(@PathVariable Integer watch_id) {
       return   watchService.deleteWatch(watch_id);
    }

    @GetMapping("get/watch/picture/{watch_id}")
    public List<Picture>   getAllPictures(@RequestParam Integer watch_id ) {
        return pictureService.findAllPictures(watch_id);

    }





}
