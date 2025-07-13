package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.CartLignRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.CartRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.OrderRequestDTO;
import com.Chronova.ChronovaStore.services.CartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CartController {
    CartService cartService;
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @PreAuthorize("hasRole('ADMIN') or #user_id == authentication.principal.id")
    @GetMapping("get/cart/{user_id}")
    public CartRequestDTO getCart(@PathVariable("user_id") Integer user_id){
        return cartService.cartToCartRequestDTO( cartService.getCartForUser(user_id));
    }
    
    @PreAuthorize("hasRole('ADMIN') or #user_id == authentication.principal.id")
    @PostMapping("add/product/{user_id}")
    public CartRequestDTO addCartLign(@PathVariable("user_id") Integer user_id,@RequestBody CartLignRequestDTO cartLignDTO){
        return cartService.cartToCartRequestDTO( cartService.addCartLignToCart(user_id,cartLignDTO));
    }



}
