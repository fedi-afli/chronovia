package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;

@Entity
public class CartLign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cart_Lign_id;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToOne
    @JoinColumn(name = "watch_id")
    private Watch watch;
    private Integer quantity;

    public CartLign(Cart cart, Watch watch, Integer quantity) {
        this.cart = cart;
        this.watch = watch;
        this.quantity = quantity;
    }

    public CartLign() {
    }

    public Long getCart_Lign_id() {
        return cart_Lign_id;
    }

    public void setCart_Lign_id(Long cart_Lign_id) {
        this.cart_Lign_id = cart_Lign_id;
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
    public double calculateTotal() {
        return watch.getPrice() * this.quantity;
    }
}
