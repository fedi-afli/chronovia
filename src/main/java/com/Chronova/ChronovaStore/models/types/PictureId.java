package com.Chronova.ChronovaStore.models.types;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PictureId implements Serializable {

    @Column(name = "id")
    private Integer id;

    @Column(name = "watch_id")
    private Integer watchId;

    public PictureId() {}

    public PictureId(Integer id, Integer watchId) {
        this.id = id;
        this.watchId = watchId;
    }

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWatchId() {
        return watchId;
    }

    public void setWatchId(Integer watchId) {
        this.watchId = watchId;
    }

    // equals() and hashCode() - mandatory for composite keys

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PictureId)) return false;
        PictureId that = (PictureId) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(watchId, that.watchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, watchId);
    }
}