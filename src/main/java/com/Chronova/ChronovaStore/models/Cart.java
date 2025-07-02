package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartLign> watches = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartLign> getWatches() {
        return watches;
    }

    public void setWatches(List<CartLign> watches) {
        this.watches = watches;
    }

    public Cart(User user, List<CartLign> watches) {
        this.user = user;
        this.watches = watches;
    }

    public Cart() {
    }
}