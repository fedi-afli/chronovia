package com.Chronova.ChronovaStore.models;

import com.Chronova.ChronovaStore.models.types.WatchMaterial;
import com.Chronova.ChronovaStore.models.types.WatchType;
import jakarta.persistence.Entity;

@Entity
public class SpecificationQuartz extends Specification{

    private String batteryType;
    private boolean isSolar;
    private Integer accuracy;

    public SpecificationQuartz(
            String modelName,
            String modelDescription,
            Long caseheight,
            Long casewidth,
            Integer modelYear,
            WatchMaterial watchMaterial,
            String movementCaliber,
            String brandName,
            WatchType watchType,
            String batteryType,
            boolean isSolar,
            Integer accuracy
    ) {
        super(modelName, modelDescription,  caseheight, casewidth, modelYear, watchMaterial, movementCaliber, brandName, watchType);
        this.batteryType = batteryType;
        this.isSolar = isSolar;
        this.accuracy = accuracy;
    }

    public SpecificationQuartz() {

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
