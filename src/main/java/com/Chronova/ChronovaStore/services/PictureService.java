package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.models.Picture;
import com.Chronova.ChronovaStore.models.Watch;
import com.Chronova.ChronovaStore.repository.PictureRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureService {
    private final PictureRepository pictureRepository;
    private final WatchService watchService;

    public PictureService(PictureRepository pictureRepository, WatchService watchService) {
        this.pictureRepository = pictureRepository;
        this.watchService = watchService;
    }

    public List<String> getPictureUrlsForWatch(Integer watchId) {
        return pictureRepository.findByWatch_Id(watchId).stream()
                .map(p -> "http://localhost:8080/api/get/picture/" + p.getId())
                .collect(Collectors.toList());
    }

    public byte[] getPictureDataById(Integer pictureId) {
        return pictureRepository.findById(pictureId)
                .map(Picture::getImageData)
                .orElseThrow(() -> new RuntimeException("Picture not found"));
    }

    @Transactional
    public List<String> savePicturesForWatch(Integer watchId, List<MultipartFile> pictureFiles) throws IOException {
        Watch watch = watchService.getWatchById(watchId);
        if (watch == null) {
            throw new IllegalArgumentException("Watch with ID " + watchId + " not found");
        }

        // Delete existing pictures for this watch
        pictureRepository.deleteByWatchId(watchId);

        // Save new pictures
        return pictureFiles.stream()
                .map(file -> {
                    try {
                        Picture picture = new Picture();
                        picture.setWatch(watch);
                        picture.setImageData(file.getBytes());
                        Picture savedPicture = pictureRepository.save(picture);
                        return "/api/get/picture/" + savedPicture.getId();
                    } catch (IOException e) {
                        throw new RuntimeException("Failed to save image", e);
                    }
                })
                .collect(Collectors.toList());
    }
}