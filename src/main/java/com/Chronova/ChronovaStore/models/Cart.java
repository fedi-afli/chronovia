package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartLign> cartLigns = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double total;

    public Cart() {}

    public Double getCartTotalPrice() {
        this.total = cartLigns.stream()
                .map(CartLign::calculateTotal)
                .reduce(0.0, Double::sum);
        return total;
    }
}

