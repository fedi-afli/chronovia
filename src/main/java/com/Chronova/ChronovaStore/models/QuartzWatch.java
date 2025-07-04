package com.Chronova.ChronovaStore.models;

import com.Chronova.ChronovaStore.models.types.WatchMaterial;
import com.Chronova.ChronovaStore.models.types.WatchType;
import jakarta.persistence.Entity;

@Entity
public class QuartzWatch extends Watch{

    private String batteryType;
    private boolean isSolar;
    private Integer accuracy;

    public QuartzWatch(
            String referenceNumber,
            double price,
            String modelName,
            String brandName,
            String modelDescription,
            Long caseWidth,
            Long caseheight,
            WatchMaterial watchMaterial,
            Integer modelYear,
            String movementCaliber,
            WatchType watchType,
            String batteryType,
            boolean isSolar,
            Integer accuracy
    ) {
        super(referenceNumber, price, modelName, brandName, modelDescription, caseWidth, caseheight, watchMaterial, modelYear, movementCaliber, watchType);
        this.batteryType = batteryType;
        this.isSolar = isSolar;
        this.accuracy = accuracy;
    }

    public QuartzWatch() {

    }

    public QuartzWatch(String s, String s1, Long aLong, Long aLong1, Integer integer, WatchMaterial watchMaterial, String s2, String s3, WatchType watchType, String s4, boolean solar, Integer accuracy) {
    }

    public String getBatteryType() {
        return batteryType;
    }

    public void setBatteryType(String batteryType) {
        this.batteryType = batteryType;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public boolean isSolar() {
        return isSolar;
    }

    public void setSolar(boolean solar) {
        isSolar = solar;
    }

    public Integer getAccuracy(){
        return this.accuracy;
    }

}
