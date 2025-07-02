package com.Chronova.ChronovaStore.models;

import com.Chronova.ChronovaStore.models.types.WatchMaterial;
import com.Chronova.ChronovaStore.models.types.WatchType;
import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Specification {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;
    @OneToOne
    private Watch watch;
    protected String modelName;
    protected String brandName;
    protected String modelDescription;
    protected Long caseWidth;
    protected Long caseheight;
    @Enumerated(EnumType.STRING)
    protected WatchMaterial watchMaterial;
    protected Integer modelYear;
    protected String movementCaliber;
    @Enumerated(EnumType.STRING)
    protected WatchType watchType;


    public Specification() {
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Specification(String modelName, String modelDescription, Long caseheight,Long casewidth, Integer modelYear, WatchMaterial watchMaterial, String movementCaliber, String brandName,WatchType watchType) {
        this.modelName = modelName;
        this.modelDescription = modelDescription;
        this.caseheight = caseheight;
        this.caseWidth = caseheight;
        this.modelYear = modelYear;
        this.watchMaterial = watchMaterial;
        this.movementCaliber = movementCaliber;
        this.brandName = brandName;
        this.watchType = watchType;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public WatchMaterial getWatchMaterial() {
        return watchMaterial;
    }

    public void setWatchMaterial(WatchMaterial watchMaterial) {
        this.watchMaterial = watchMaterial;
    }

    public Watch getWatch() {
        return watch;
    }

    public void setWatch(Watch watch) {
        this.watch = watch;
    }

    public Long getCaseWidth() {
        return caseWidth;
    }

    public void setCaseWidth(Long caseWidth) {
        this.caseWidth = caseWidth;
    }

    public Long getCaseheight() {
        return caseheight;
    }

    public void setCaseheight(Long caseheight) {
        this.caseheight = caseheight;
    }

    public WatchType getWatchType() {
        return watchType;
    }

    public void setWatchType(WatchType watchType) {
        this.watchType = watchType;
    }

    public Integer getModelYear() {
        return modelYear;
    }

    public void setModelYear(Integer modelYear) {
        this.modelYear = modelYear;
    }

    public String getMovementCaliber() {
        return movementCaliber;
    }

    public void setMovementCaliber(String movementCaliber) {
        this.movementCaliber = movementCaliber;
    }
}
