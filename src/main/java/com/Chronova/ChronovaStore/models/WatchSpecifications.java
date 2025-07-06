package com.Chronova.ChronovaStore.models;

import com.Chronova.ChronovaStore.models.types.WatchMaterial;
import com.Chronova.ChronovaStore.models.types.WatchType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

    public class WatchSpecifications {

        public static Specification<Watch> withAdvancedFilters(
                String referenceNumber,
                Double minPrice,
                Double maxPrice,
                String modelName,
                String brandName,
                Integer minCaseWidth,
                Integer maxCaseWidth,
                WatchMaterial watchMaterial,
                WatchType watchType,
                Integer modelYear,
                String movementCaliber,
                String batteryType,
                Boolean isSolar,
                Boolean isSelfWind,
                Integer jewelCount
        ) {
            return (root, query, cb) -> {
                Predicate p = cb.conjunction();

                if (referenceNumber != null && !referenceNumber.isEmpty()) {
                    System.out.println("Filtering by referenceNumber: " + referenceNumber);
                    p = cb.and(p, cb.like(cb.lower(root.get("referenceNumber")), "%" + referenceNumber.toLowerCase() + "%"));
                }

                if (minPrice != null) {
                    System.out.println("Filtering by minPrice: " + minPrice);
                    p = cb.and(p, cb.greaterThanOrEqualTo(root.get("price"), minPrice));
                }

                if (maxPrice != null) {
                    System.out.println("Filtering by maxPrice: " + maxPrice);
                    p = cb.and(p, cb.lessThanOrEqualTo(root.get("price"), maxPrice));
                }

                if (modelName != null && !modelName.isEmpty()) {
                    System.out.println("Filtering by modelName: " + modelName);
                    p = cb.and(p, cb.like(cb.lower(root.get("modelName")), "%" + modelName.toLowerCase() + "%"));
                }

                if (brandName != null && !brandName.isEmpty()) {
                    System.out.println("Filtering by brandName: " + brandName);
                    p = cb.and(p, cb.like(cb.lower(root.get("brandName")), "%" + brandName.toLowerCase() + "%"));
                }

                if (minCaseWidth != null) {
                    System.out.println("Filtering by minCaseWidth: " + minCaseWidth);
                    p = cb.and(p, cb.greaterThanOrEqualTo(root.get("caseWidth"), minCaseWidth));
                }

                if (maxCaseWidth != null) {
                    System.out.println("Filtering by maxCaseWidth: " + maxCaseWidth);
                    p = cb.and(p, cb.lessThanOrEqualTo(root.get("caseWidth"), maxCaseWidth));
                }

                if (watchMaterial != null) {
                    System.out.println("Filtering by watchMaterial: " + watchMaterial);
                    p = cb.and(p, cb.equal(root.get("watchMaterial"), watchMaterial));
                }

                if (watchType != null) {
                    System.out.println("Filtering by watchType: " + watchType);
                    p = cb.and(p, cb.equal(root.get("watchType"), watchType));
                }

                if (modelYear != null) {
                    System.out.println("Filtering by modelYear: " + modelYear);
                    p = cb.and(p, cb.equal(root.get("modelYear"), modelYear));
                }

                if (movementCaliber != null && !movementCaliber.isEmpty()) {
                    System.out.println("Filtering by movementCaliber: " + movementCaliber);
                    p = cb.and(p, cb.like(cb.lower(root.get("movementCaliber")), "%" + movementCaliber.toLowerCase() + "%"));
                }

                if (batteryType != null && !batteryType.isEmpty()) {
                    System.out.println("Filtering by batteryType: " + batteryType);
                    p = cb.and(p, cb.like(cb.lower(root.get("batteryType")), "%" + batteryType.toLowerCase() + "%"));
                }

                if (isSolar != null) {
                    System.out.println("Filtering by isSolar: " + isSolar);
                    p = cb.and(p, cb.equal(root.get("isSolar"), isSolar));
                }

                if (isSelfWind != null) {
                    System.out.println("Filtering by isSelfWind: " + isSelfWind);
                    p = cb.and(p, cb.equal(root.get("isSelfWind"), isSelfWind));
                }

                if (jewelCount != null) {
                    System.out.println("Filtering by jewelCount: " + jewelCount);
                    p = cb.and(p, cb.equal(root.get("jewelCount"), jewelCount));
                }

                System.out.println("Final predicate built.");
                return p;
            };
        }



    }

