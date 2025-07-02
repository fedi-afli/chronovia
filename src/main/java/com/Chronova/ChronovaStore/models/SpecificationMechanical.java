package com.Chronova.ChronovaStore.models;

import com.Chronova.ChronovaStore.models.types.WatchMaterial;
import com.Chronova.ChronovaStore.models.types.WatchType;
import jakarta.persistence.Entity;

@Entity
public class SpecificationMechanical extends Specification {
    private boolean isSelfWind;
    private Integer powerReserveHours;
    private Integer jewelCount;

    public SpecificationMechanical() {
    }

    public SpecificationMechanical(
            String modelName,
            String modelDescription,
            Long caseheight,
            Long casewidth,
            Integer modelYear,
            WatchMaterial watchMaterial,
            String movementCaliber,
            String brandName,
            WatchType watchType,
            boolean isSelfWind,
            Integer powerReserveHours,
            Integer jewelCount
    ) {
        super(modelName, modelDescription, caseheight,casewidth, modelYear, watchMaterial, movementCaliber, brandName, watchType);
        this.isSelfWind = isSelfWind;
        this.powerReserveHours = powerReserveHours;
        this.jewelCount = jewelCount;
    }


    public boolean isSelfWind() {
        return isSelfWind;
    }

    public void setSelfWind(boolean selfWind) {
        isSelfWind = selfWind;
    }

    public Integer getJewelCount() {
        return jewelCount;
    }

    public void setJewelCount(Integer jewelCount) {
        this.jewelCount = jewelCount;
    }

    public Integer getPowerReserveHours() {
        return powerReserveHours;
    }

    public void setPowerReserveHours(Integer powerReserveHours) {
        this.powerReserveHours = powerReserveHours;
    }
}
