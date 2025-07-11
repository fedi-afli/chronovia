package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.dataDTO.CartLignRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.CartRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.OrderRequestDTO;

import com.Chronova.ChronovaStore.models.Cart;
import com.Chronova.ChronovaStore.models.CartLign;

import com.Chronova.ChronovaStore.models.Order;
import com.Chronova.ChronovaStore.repository.CartLignRepository;
import com.Chronova.ChronovaStore.repository.CartRepository;
import com.Chronova.ChronovaStore.repository.OrderRepository;
import com.Chronova.ChronovaStore.repository.WatchRepository;


import org.springframework.stereotype.Service;


import java.time.LocalDateTime;


import java.util.stream.Collectors;

@Service
public class CartService  {


    private final WatchRepository watchRepository;
    private final  CartRepository cartRepository;
    private final CartLignRepository cartLignRepository;
    private final OrderRepository orderRepository;


    public CartService(CartRepository repository, WatchRepository watchRepository, CartLignRepository cartLignRepository, OrderService orderService, OrderRepository orderRepository) {

        this.cartRepository = repository;
        this.watchRepository = watchRepository;
        this.cartLignRepository = cartLignRepository;
        this.orderRepository = orderRepository;
    }

   public Cart getCartForUser(Integer userId){
       return cartRepository.findByUserId(userId);

   }
    public Cart addCartLignToCart(Integer userId, CartLignRequestDTO cartLignDTO) {
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

        return cart;
    }

   public CartRequestDTO cartToCartRequestDTO(Cart cart){
       return new CartRequestDTO(cart.getCartLigns().stream().map(elem->new CartLignRequestDTO(elem.getQuantity(),elem.getWatch().getId())).collect(Collectors.toUnmodifiableList()), cart.getTotal(),cart.getUser().getId());
   }


   public Order confirmCart(Integer user_id){
        OrderRequestDTO orderRequestDTO=  new OrderRequestDTO(cartToCartRequestDTO( getCartForUser(user_id)).userID(), LocalDateTime.now());
        return orderRepository.save(orderRequestDTO);


   }






}
