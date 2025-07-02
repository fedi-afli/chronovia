package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;

@Entity
public class CartLign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cart cart;

    @ManyToOne
    private Watch watch;

    private Integer quantity;

    public CartLign(Cart cart, Watch watch, Integer quantity) {
        this.cart = cart;
        this.watch = watch;
        this.quantity = quantity;
    }

    public CartLign() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Watch getWatch() {
        return watch;
    }

    public void setWatch(Watch watch) {
        this.watch = watch;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
