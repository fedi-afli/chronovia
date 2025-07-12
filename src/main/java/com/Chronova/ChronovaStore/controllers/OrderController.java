package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.OrderRequestDTO;
import com.Chronova.ChronovaStore.services.CartService;
import com.Chronova.ChronovaStore.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class OrderController {


    private final UserService userService;
    private final CartService cartService;

    public OrderController( UserService userService, CartService cartService) {

        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping
    @RequestMapping("/order/confirm")
    public OrderRequestDTO confirmOrder(@RequestBody OrderRequestDTO orderRequestDTO){
      return cartService.orderTOOrderRequestDTO(  cartService.saveOrder(orderRequestDTO));
    }
    @PostMapping
    @RequestMapping("/orders/{user_id}")
    public List<OrderRequestDTO> getAllOrdersForUser(@PathVariable("user_id") Integer user_id){
        return  cartService.getAllOrdersForUser( user_id).stream().map(cartService::orderTOOrderRequestDTO).collect(Collectors.toUnmodifiableList());
    }

    @GetMapping
    @RequestMapping("/order/confirm/{user_id}")
    public OrderRequestDTO ConfirmCart(@PathVariable("user_id") Integer user_id){
        return cartService.orderTOOrderRequestDTO(cartService.confirmCart(user_id));

    }
}
