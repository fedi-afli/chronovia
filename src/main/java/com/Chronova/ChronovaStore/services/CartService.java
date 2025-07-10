package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.dataDTO.CartLignRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.CartRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.UserRequestDTO;
import com.Chronova.ChronovaStore.models.Cart;
import com.Chronova.ChronovaStore.models.CartLign;
import com.Chronova.ChronovaStore.models.User;
import com.Chronova.ChronovaStore.repository.CartLignRepository;
import com.Chronova.ChronovaStore.repository.CartRepository;
import com.Chronova.ChronovaStore.repository.WatchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CartService  {
    UserService userService;
    WatchRepository watchRepository;
    CartRepository cartRepository;
   CartLignRepository cartLignRepository;
    public CartService(UserService userService, CartRepository repository, WatchRepository watchRepository, CartLignRepository cartLignRepository) {
        this.userService = userService;
        this.cartRepository = repository;
        this.watchRepository = watchRepository;
        this.cartLignRepository = cartLignRepository;
    }

   public CartRequestDTO getCartForUser(Integer userId){
       return cartToCartRequestDTO(cartRepository.findByUserId(userId));

   }
    public CartRequestDTO addCartLignToCart(Integer userId, CartLignRequestDTO cartLignDTO) {
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new RuntimeException("Cart not found for user ID: " + userId);
        }

        CartLign cartLign = new CartLign();
        cartLign.setCart(cart);
        cartLign.setWatch(watchRepository.findById(cartLignDTO.watchId()).orElse(null));
        cartLign.setQuantity(cartLignDTO.quantity());
        cartLign.setQuantity(cartLignDTO.quantity());

        cartLignRepository.save(cartLign);

        cart.addCartLign(cartLign);
        cart.setTotal(cart.getTotal() + cartLign.calculateTotal());
        cartRepository.save(cart);

        return cartToCartRequestDTO(cart);
    }

   public CartRequestDTO cartToCartRequestDTO(Cart cart){
       return new CartRequestDTO(cart.getCartLigns().stream().map(elem->new CartLignRequestDTO(elem.getQuantity(),elem.getWatch().getId())).collect(Collectors.toUnmodifiableList()), cart.getTotal(),cart.getUser().getId());
   }




}
