package com.Chronova.ChronovaStore.repository;

import com.Chronova.ChronovaStore.models.CartLign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartLignRepository extends JpaRepository<CartLign,Integer> {
}
