package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.dataDTO.OrderRequestDTO;
import com.Chronova.ChronovaStore.models.Cart;
import com.Chronova.ChronovaStore.models.Order;
import com.Chronova.ChronovaStore.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {



    private final CartService cartService;
    private final OrderRepository orderRepository;

    public OrderService(UserService userService, CartService cartService, OrderRepository orderRepository) {

        this.cartService = cartService;
        this.orderRepository = orderRepository;
    }

    public Order orderRequestDTOToOrder(OrderRequestDTO orderRequestDTO){

        return new Order(cartService.getCartForUser(orderRequestDTO.userId()), LocalDateTime.now());

    }

        public OrderRequestDTO orderTOOrderRequestDTO(Order order){

        return new OrderRequestDTO(order.getCart().getCartId(), LocalDateTime.now());

    }

    public Order saveOrder(OrderRequestDTO orderRequestDTO) {
       return orderRepository.save(orderRequestDTOToOrder(orderRequestDTO));

    }
    public List<Order> getAllOrdersForUser(Integer user_id){
        return   orderRepository.findByUserId(user_id);
    }


}
