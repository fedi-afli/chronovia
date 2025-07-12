package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.models.Picture;
import com.Chronova.ChronovaStore.repository.PictureRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureService {

    PictureRepository pictureRepository;
    public PictureService(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    public List<Picture> findAllPictures(Integer watch_id) {
        return pictureRepository.findByWatch_Id(watch_id) ;
    }
}
