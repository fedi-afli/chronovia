package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateCommande;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<OrderLign> lignes = new ArrayList<>();

    @OneToOne(mappedBy = "commande", cascade = CascadeType.ALL)
    private Reciept reciept;

    public Order(List<OrderLign> lignes, User user, Reciept reciept, LocalDateTime dateCommande) {
        this.lignes = lignes;
        this.user = user;
        this.reciept = reciept;
        this.dateCommande = dateCommande;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderLign> getLignes() {
        return lignes;
    }

    public void setLignes(List<OrderLign> lignes) {
        this.lignes = lignes;
    }

    public Reciept getReciept() {
        return reciept;
    }

    public void setReciept(Reciept reciept) {
        this.reciept = reciept;
    }
}

