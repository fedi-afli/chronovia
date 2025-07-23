package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.models.Picture;
import com.Chronova.ChronovaStore.services.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PictureController {
    private final PictureService pictureService;

    @Autowired
    public PictureController(PictureService pictureService) {
        this.pictureService = pictureService;
    }

    /**
     * Get list of image URLs for a specific watch
     */
    @GetMapping("/get/watch/picture/{watchId}")
    public ResponseEntity<List<String>> getPictureUrlsForWatch(@PathVariable Integer watchId) {
        List<String> imageUrls = pictureService.getPictureUrlsForWatch(watchId);
        return ResponseEntity.ok(imageUrls);
    }

    /**
     * Serve actual image data
     */
    @GetMapping(
            value = "/get/picture/{pictureId}",
            produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE}
    )
    public ResponseEntity<byte[]> getPictureData(@PathVariable Integer pictureId) {
        byte[] imageData = pictureService.getPictureDataById(pictureId);
        return ResponseEntity.ok()
                .header("Cache-Control", "max-age=604800") // Cache for 1 week
                .body(imageData);
    }

    /**
     * Upload new images for a watch
     */
    @PostMapping("/save/watch/{watchId}/images")
    public ResponseEntity<List<String>> saveImages(
            @PathVariable Integer watchId,
            @RequestParam("images") List<MultipartFile> images) throws IOException {
        List<String> newImageUrls = pictureService.savePicturesForWatch(watchId, images);
        return ResponseEntity.ok(newImageUrls);
    }
}