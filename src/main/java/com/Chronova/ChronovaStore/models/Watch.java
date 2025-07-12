package com.Chronova.ChronovaStore.models;

import com.Chronova.ChronovaStore.models.types.WatchMaterial;
import com.Chronova.ChronovaStore.models.types.WatchType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Watch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer watch_id;

    @OneToOne(mappedBy = "watch")
    private CartLign cartLign;

    private String referenceNumber;
    private double price;
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

    @OneToMany(mappedBy = "watch", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Picture> pictures = new ArrayList<>();


    public Watch(String referenceNumber, double price, String modelName, String brandName, String modelDescription, Long caseWidth, Long caseheight, WatchMaterial watchMaterial, Integer modelYear, String movementCaliber, WatchType watchType) {
        this.referenceNumber = referenceNumber;
        this.price = price;
        this.modelName = modelName;
        this.brandName = brandName;
        this.modelDescription = modelDescription;
        this.caseWidth = caseWidth;
        this.caseheight = caseheight;
        this.watchMaterial = watchMaterial;
        this.modelYear = modelYear;
        this.movementCaliber = movementCaliber;
        this.watchType = watchType;
    }

    public Watch() {
    }

    public Integer getId() {
        return watch_id;
    }

    public void setId(Integer id) {
        this.watch_id = id;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public Long getCaseheight() {
        return caseheight;
    }

    public void setCaseheight(Long caseheight) {
        this.caseheight = caseheight;
    }

    public Long getCaseWidth() {
        return caseWidth;
    }

    public void setCaseWidth(Long caseWidth) {
        this.caseWidth = caseWidth;
    }

    public WatchMaterial getWatchMaterial() {
        return watchMaterial;
    }

    public void setWatchMaterial(WatchMaterial watchMaterial) {
        this.watchMaterial = watchMaterial;
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

    public WatchType getWatchType() {
        return watchType;
    }

    public void setWatchType(WatchType watchType) {
        this.watchType = watchType;
    }
}

