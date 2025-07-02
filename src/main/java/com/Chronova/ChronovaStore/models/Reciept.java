package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Reciept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateFacture;
    private Double montantTotal;

    @OneToOne
    private Order commande;

    public Reciept(LocalDateTime dateFacture, Double montantTotal, Order commande) {
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
        this.commande = commande;
    }

    public Reciept() {

    }

    public Reciept(Long id, LocalDateTime dateFacture, Double montantTotal, Order commande) {
        this.id = id;
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
        this.commande = commande;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(LocalDateTime dateFacture) {
        this.dateFacture = dateFacture;
    }

    public Double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public Order getCommande() {
        return commande;
    }

    public void setCommande(Order commande) {
        this.commande = commande;
    }
}
