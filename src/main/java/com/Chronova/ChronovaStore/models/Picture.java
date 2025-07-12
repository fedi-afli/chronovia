package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;

@Entity
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "watch_id")
    private Watch watch;

    public Picture() {}

    public Picture(byte[] imageData, Watch watch) {
        this.imageData = imageData;
        this.watch = watch;
    }

    // Getters and setters
    public Integer getId() {
        return id;
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
