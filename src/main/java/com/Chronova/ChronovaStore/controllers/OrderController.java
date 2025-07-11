package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.OrderRequestDTO;
import com.Chronova.ChronovaStore.services.OrderService;
import com.Chronova.ChronovaStore.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;

    public OrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping
    @RequestMapping("/order/confirm")
    public OrderRequestDTO confirmOrder(@RequestBody OrderRequestDTO orderRequestDTO){
      return orderService.orderTOOrderRequestDTO(  orderService.saveOrder(orderRequestDTO));
    }
    @PostMapping
    @RequestMapping("/orders/{user_id}")
    public List<OrderRequestDTO> getAllOrdersForUser(@PathVariable("user_id") Integer user_id){
        return  orderService.getAllOrdersForUser( user_id).stream().map(orderService::orderTOOrderRequestDTO).collect(Collectors.toUnmodifiableList());
    }
}
