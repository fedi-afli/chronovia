package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "total")
    private Double total;

    private LocalDateTime orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLign> orderLigns = new ArrayList<>();

    public Order() {
        this.orderDate = LocalDateTime.now();
    }

    public Order(Integer userId, Double total) {
        this.userId = userId;
        this.total = total;
        this.orderDate = LocalDateTime.now();
    }

    public void addOrderLign(OrderLign orderLign) {
        this.orderLigns.add(orderLign);
        orderLign.setOrder(this);
    }

    // Getters and setters...
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public LocalDateTime getOrderDate() { return orderDate; }
    public void setOrderDate(LocalDateTime orderDate) { this.orderDate = orderDate; }

    public List<OrderLign> getOrderLigns() { return orderLigns; }
    public void setOrderLigns(List<OrderLign> orderLigns) { this.orderLigns = orderLigns; }
    public Double getOrderTotal() {
        return orderLigns.stream()
                .mapToDouble(OrderLign::getLineTotal)
                .sum();
    }


}
