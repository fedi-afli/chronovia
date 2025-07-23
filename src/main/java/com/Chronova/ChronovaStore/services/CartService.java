package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.dataDTO.CartLignRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.CartRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.OrderRequestDTO;
import com.Chronova.ChronovaStore.models.*;
import com.Chronova.ChronovaStore.repository.CartLignRepository;
import com.Chronova.ChronovaStore.repository.CartRepository;
import com.Chronova.ChronovaStore.repository.OrderRepository;
import com.Chronova.ChronovaStore.repository.WatchRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final WatchRepository watchRepository;
    private final CartRepository cartRepository;
    private final CartLignRepository cartLignRepository;
    private final OrderRepository orderRepository;

    public CartService(CartRepository repository,
                       WatchRepository watchRepository,
                       CartLignRepository cartLignRepository,
                       OrderRepository orderRepository) {

        this.cartRepository = repository;
        this.watchRepository = watchRepository;
        this.cartLignRepository = cartLignRepository;
        this.orderRepository = orderRepository;
    }

    public Cart getCartForUser(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addCartLignToCart(Integer userId, CartLignRequestDTO dto) {
        Cart cart = getCartForUser(userId);

        Optional<CartLign> existingLignOpt = cartLignRepository
                .findByUserAndWatch(userId, dto.watchId());

        if (existingLignOpt.isPresent()) {
            CartLign existingLign = existingLignOpt.get();
            existingLign.setQuantity(existingLign.getQuantity() + dto.quantity());
            cartLignRepository.save(existingLign);

        } else {
            Watch watch = watchRepository.findById(dto.watchId())
                    .orElseThrow(() -> new RuntimeException("Watch not found"));

            CartLign newLign = new CartLign();
            newLign.setCart(cart);
            newLign.setWatch(watch);
            newLign.setQuantity(dto.quantity());

            cartLignRepository.save(newLign);
        }
        cart.setCartTotalPrice();
        cartRepository.save(cart);
        return cart;
    }


    public CartRequestDTO cartToCartRequestDTO(Cart cart) {
        return new CartRequestDTO(
                cart.getCartLigns().stream()
                        .map(elem -> new CartLignRequestDTO(
                                elem.getQuantity(),
                                elem.getWatch().getId()))
                        .collect(Collectors.toUnmodifiableList()),
                cart.getTotal(),
                cart.getUser().getId()
        );
    }




    public OrderRequestDTO orderTOOrderRequestDTO(Order order) {
        return new OrderRequestDTO(order.getUserId(), order.getOrderDate(),order.getTotal());
    }





    public Order confirmCart(Integer user_id) {
        Cart cart = cartRepository.findByUserId(user_id);
        if (cart == null) throw new RuntimeException("Cart not found");

        Order order = new Order(cart.getUser().getId(), cart.getTotal());

        for (CartLign cartLign : cart.getCartLigns()) {
            OrderLign orderLign = new OrderLign(
                    order,
                    cartLign.getWatch(),
                    cartLign.getQuantity()
            );
            order.addOrderLign(orderLign);
        }

        return orderRepository.save(order);
    }
    @Transactional
    public void clearCart(Integer userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new RuntimeException("Cart not found for user ID: " + userId);
        }

        // Delete all CartLign records for this cart
        cartLignRepository.deleteByCartId(cart.getCartId());

        // Reset the cart total
        cart.setTotal(0.0);

        // Save the empty cart
        cartRepository.save(cart);
    }

    public void removeCartLign(Integer userId, CartLignRequestDTO cartLignDTO) {
       cartLignRepository.deleteByUserIdAndWatchId(userId,cartLignDTO.watchId());
       Cart cart=cartRepository.findByUserId(userId);
       cart.setCartTotalPrice();
       cartRepository.save(cart);
    }

    public void setQautity(Integer userId, CartLignRequestDTO cartLignDTO) {
        cartLignRepository.updateQuantityByUserIdAndWatchId(userId,cartLignDTO.watchId(), cartLignDTO.quantity());
        Cart cart=cartRepository.findByUserId(userId);
        cart.setCartTotalPrice();
        cartRepository.save(cart);
    }
}
