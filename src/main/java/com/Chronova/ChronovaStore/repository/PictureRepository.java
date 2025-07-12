package com.Chronova.ChronovaStore.repository;

import com.Chronova.ChronovaStore.models.Picture;
import com.Chronova.ChronovaStore.models.Watch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepository  extends JpaRepository<Picture, Integer> {
    List<Picture> findByWatch_Id(Integer watch_id);

}
