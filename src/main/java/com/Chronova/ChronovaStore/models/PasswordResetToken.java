package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne
    private User user;

    private LocalDateTime expiryDate;

    private boolean used = false;

    public PasswordResetToken() {}

    public PasswordResetToken(String token, User user, LocalDateTime expiryDate) {
        this.token = token;
        this.user = user;
        this.expiryDate = expiryDate;
    }



    public boolean isExpired() {
        return expiryDate.isBefore(LocalDateTime.now());
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean b) {
        this.used = b;
    }

    public User getUser() {
        return user;
    }
}
