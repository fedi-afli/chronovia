package com.Chronova.ChronovaStore.models;

import jakarta.persistence.*;

@Entity
public class OrderLign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "watch_id")
    private Watch watch;

    private Integer quantity;





    public OrderLign(Order order, Watch watch, Integer quantity) {
        this.order = order;
        this.watch = watch;
        this.quantity = quantity;
    }
    public Double getLineTotal() {
        return (watch != null && quantity != null) ? watch.getPrice() * quantity : 0.0;
    }



    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Watch getWatch() { return watch; }
    public void setWatch(Watch watch) { this.watch = watch; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }


}
