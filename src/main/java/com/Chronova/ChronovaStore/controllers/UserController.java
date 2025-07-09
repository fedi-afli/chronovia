package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.UserRequestDTO;
import com.Chronova.ChronovaStore.services.CartService;
import com.Chronova.ChronovaStore.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {
    UserService userService;
    CartService cartService;
    public UserController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }
    @PostMapping("save/user")
    public UserRequestDTO saveUser(@RequestBody UserRequestDTO userRequestDTO) {
        return    userService.saveUser(userRequestDTO);
    }
    @GetMapping("get/user")
    public List<UserRequestDTO> getUsers() {
        return    userService.getUsers();
    }
    @GetMapping("search/user/{user_id}")
    public UserRequestDTO getUserById(@PathVariable  Integer user_id) {
        return    userService.getUserById(user_id);
    }



}
