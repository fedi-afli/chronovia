package com.Chronova.ChronovaStore.dataDTO;

import com.Chronova.ChronovaStore.models.Picture;

import java.util.List;

public record PictureRequestDTo(
        Integer watch_id,
        List<Picture> pictures
) {
}
