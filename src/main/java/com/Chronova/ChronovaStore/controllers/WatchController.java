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
    private final PictureService pictureService;


    public WatchController(WatchService watchService, WatchRepository watchRepository, PictureService pictureService) {
        this.watchService = watchService;
        this.pictureService = pictureService;
    }

    
    @PostMapping("/save/watch")
    public WatchRequestDTO saveWatch(
            @RequestPart("watch") WatchRequestDTO watchRequestDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) throws IOException {

        Watch savedWatch = watchService.save(watchRequestDTO, images);
        return watchService.watchToWatchRequestDTO(savedWatch);
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
    public WatchRequestDTO   getWatch(@RequestParam Integer watch_id ) {
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
