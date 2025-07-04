package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;



@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    private String password;
    private String username;
    private String email;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;


    public User() {}

    public User(String password, String username, String email) {
        this.password = password;
        this.username = username;
        this.email = email;
    }

    // Getters and setters...

    public Long getId() {
        return user_id;
    }

    public void setId(Long id) {
        this.user_id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
}
