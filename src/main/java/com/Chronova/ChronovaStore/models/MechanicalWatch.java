package com.Chronova.ChronovaStore.models;

import com.Chronova.ChronovaStore.models.types.WatchMaterial;
import com.Chronova.ChronovaStore.models.types.WatchType;
import jakarta.persistence.Entity;

@Entity
public class MechanicalWatch extends Watch {
    private boolean isSelfWind;
    private Integer powerReserveHours;
    private Integer jewelCount;


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

    public Integer getJewelCount() {
        return jewelCount;
    }

    public void setJewelCount(Integer jewelCount) {
        this.jewelCount = jewelCount;
    }

    public MechanicalWatch() {
    }

    public MechanicalWatch(
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
            boolean isSelfWind,
            Integer powerReserveHours,
            Integer jewelCount
    ) {
        super(referenceNumber, price, modelName, brandName, modelDescription, caseWidth, caseheight, watchMaterial, modelYear, movementCaliber, watchType);
        this.isSelfWind = isSelfWind;
        this.powerReserveHours = powerReserveHours;
        this.jewelCount = jewelCount;
    }



}

