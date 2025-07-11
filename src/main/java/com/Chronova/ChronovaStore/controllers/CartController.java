package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.CartLignRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.CartRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.OrderRequestDTO;
import com.Chronova.ChronovaStore.services.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class CartController {
    CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @GetMapping("get/cart/{user_id}")
    public CartRequestDTO getCart(@PathVariable("user_id") Integer user_id){
        return cartService.cartToCartRequestDTO( cartService.getCartForUser(user_id));
    }
    @PostMapping("add/product/{user_id}")
    public CartRequestDTO addCartLign(@PathVariable("user_id") Integer user_id,@RequestBody CartLignRequestDTO cartLignDTO){
        return cartService.cartToCartRequestDTO( cartService.addCartLignToCart(user_id,cartLignDTO));
    }

//    @GetMapping("/confim/cart/{cart_id}")
//    public OrderRequestDTO  confimCart(@PathVariable("cart_id") Integer cart_id){
//        return cartService.confirmCart(Integer  cart_id);
//    }



}
