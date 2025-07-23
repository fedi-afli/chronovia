package com.Chronova.ChronovaStore.repository;

import com.Chronova.ChronovaStore.models.CartLign;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartLignRepository extends JpaRepository<CartLign,Integer> {
   @Query("SELECT cl FROM CartLign cl " +
           "WHERE cl.cart.user.user_id = :userId " +
           "AND cl.watch.watch_id = :watchId")
   Optional<CartLign> findByUserAndWatch(@Param("userId") Integer userId,
                                         @Param("watchId") Integer watchId);

   @Modifying
   @Transactional
   @Query("DELETE FROM CartLign c WHERE c.cart.cartId = :cartId")
   void deleteByCartId(@Param("cartId") Integer cartId);
   @Modifying
   @Transactional
   @Query("DELETE FROM CartLign cl WHERE cl.cart.user.user_id = :userId AND cl.watch.watch_id = :watchId")
   void deleteByUserIdAndWatchId(Integer userId, Integer watchId);
   @Modifying
   @Transactional
   @Query("UPDATE CartLign cl SET cl.quantity = :quantity WHERE cl.cart.user.user_id = :userId AND cl.watch.watch_id = :watchId")
   void updateQuantityByUserIdAndWatchId(Integer userId, Integer watchId, Integer quantity);
}
