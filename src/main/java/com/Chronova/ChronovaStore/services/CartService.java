package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.dataDTO.CartLignRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.CartRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.OrderRequestDTO;
import com.Chronova.ChronovaStore.models.Cart;
import com.Chronova.ChronovaStore.models.CartLign;
import com.Chronova.ChronovaStore.models.Order;
import com.Chronova.ChronovaStore.models.OrderLign;
import com.Chronova.ChronovaStore.repository.CartLignRepository;
import com.Chronova.ChronovaStore.repository.CartRepository;
import com.Chronova.ChronovaStore.repository.OrderRepository;
import com.Chronova.ChronovaStore.repository.WatchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    public Cart addCartLignToCart(Integer userId, CartLignRequestDTO cartLignDTO) {
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null) {
            throw new RuntimeException("Cart not found for user ID: " + userId);
        }

        CartLign cartLign = new CartLign();
        cartLign.setCart(cart);
        cartLign.setWatch(watchRepository.findById(cartLignDTO.watchId()).orElse(null));
        cartLign.setQuantity(cartLignDTO.quantity());

        cartLignRepository.save(cartLign);

        cart.addCartLign(cartLign);
        cart.setTotal(cart.getTotal() + cartLign.calculateTotal());
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


    public Order orderRequestDTOToOrder(OrderRequestDTO orderRequestDTO) {
        Cart cart = getCartForUser(orderRequestDTO.userId());
        Order order = new Order(cart.getUser().getId(), cart.getTotal());

        for (CartLign cartLign : cart.getCartLigns()) {
            OrderLign orderLign = new OrderLign(
                    order,
                    cartLign.getWatch(),
                    cartLign.getQuantity()
            );
            order.addOrderLign(orderLign);
        }

        return order;
    }

    public OrderRequestDTO orderTOOrderRequestDTO(Order order) {
        return new OrderRequestDTO(order.getUserId(), order.getOrderDate(),order.getTotal());
    }

    public Order saveOrder(OrderRequestDTO orderRequestDTO) {
        return orderRepository.save(orderRequestDTOToOrder(orderRequestDTO));
    }

    public List<Order> getAllOrdersForUser(Integer user_id) {
        return orderRepository.findByUserId(user_id);
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
}
