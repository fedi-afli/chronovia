package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;

@Entity
public class Watch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String referenceNumber;
    private double price;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "specification_id")
    private Specification specification;

    public Watch() {}

    public Watch( String referenceNumber, double price, Specification specification) {

        this.referenceNumber = referenceNumber;
        this.price = price;
        this.specification = specification;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }
}

