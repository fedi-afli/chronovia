package com.Chronova.ChronovaStore.dataDTO;

import com.Chronova.ChronovaStore.models.Watch;
import com.Chronova.ChronovaStore.models.types.WatchMaterial;
import com.Chronova.ChronovaStore.models.types.WatchType;

public class WatchDTO {
    private String referenceNumber;
    private double price;

    private String modelName;
    private String brandName;
    private String modelDescription;
    private Long caseWidth;
    private Long caseHeight;
    private WatchMaterial watchMaterial;
    private WatchType watchType;
    private Integer modelYear;
    private String movementCaliber;

    // Fields for SpecificationQuartz
    private String batteryType;
    private boolean isSolar;
    private Integer accuracy;

    // Fields for SpecificationMechanical
    private boolean isSelfWind;
    private Integer powerReserveHours;
    private Integer jewelCount;

    public Integer getJewelCount() {
        return jewelCount;
    }

    public void setJewelCount(Integer jewelCount) {
        this.jewelCount = jewelCount;
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

    public Long getCaseWidth() {
        return caseWidth;
    }

    public void setCaseWidth(Long caseWidth) {
        this.caseWidth = caseWidth;
    }

    public Long getCaseHeight() {
        return caseHeight;
    }

    public void setCaseHeight(Long caseHeight) {
        this.caseHeight = caseHeight;
    }

    public WatchMaterial getWatchMaterial() {
        return watchMaterial;
    }

    public void setWatchMaterial(WatchMaterial watchMaterial) {
        this.watchMaterial = watchMaterial;
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

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    public boolean isSolar() {
        return isSolar;
    }

    public void setSolar(boolean solar) {
        isSolar = solar;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public boolean isSelfWind() {
        return isSelfWind;
    }

    public void setSelfWind(boolean selfWind) {
        isSelfWind = selfWind;
    }

    public Integer getPowerReserveHours() {
        return powerReserveHours;
    }

    public void setPowerReserveHours(Integer powerReserveHours) {
        this.powerReserveHours = powerReserveHours;
    }
}
