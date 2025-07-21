package com.Chronova.ChronovaStore.models;

import com.Chronova.ChronovaStore.models.types.PictureId;
import jakarta.persistence.*;

@Entity
public class Picture {

    @EmbeddedId
    private PictureId pictureId;

    @Lob
    @Column(name = "image_data", columnDefinition = "bytea")
    private byte[] imageData;

    @MapsId("watchId") // maps the watchId attribute of PictureId
    @ManyToOne
    @JoinColumn(name = "watch_id")
    private Watch watch;

    public Picture() {}

    public Picture(PictureId pictureId, byte[] imageData, Watch watch) {
        this.pictureId = pictureId;
        this.imageData = imageData;
        this.watch = watch;
    }

    // Getters and setters

    public PictureId getPictureId() {
        return pictureId;
    }

    public void setPictureId(PictureId pictureId) {
        this.pictureId = pictureId;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public Watch getWatch() {
        return watch;
    }

    public void setWatch(Watch watch) {
        this.watch = watch;
    }
}
