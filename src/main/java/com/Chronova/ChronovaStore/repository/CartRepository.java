package com.Chronova.ChronovaStore.repository;

import com.Chronova.ChronovaStore.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {
    Cart findByUser_Id(Integer userId);
}
