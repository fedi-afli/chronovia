package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;

@Entity
public class OrderLign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Order commande;

    @ManyToOne
    private Watch produit;

    private Integer quantite;
    private Double prixUnitaire;

    public OrderLign() {
    }

    public OrderLign(Order commande, Watch produit, Integer quantite, Double prixUnitaire) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getCommande() {
        return commande;
    }

    public void setCommande(Order commande) {
        this.commande = commande;
    }

    public Watch getProduit() {
        return produit;
    }

    public void setProduit(Watch produit) {
        this.produit = produit;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }
}
