package com.Chronova.ChronovaStore.controllers;

import com.Chronova.ChronovaStore.dataDTO.UserRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.UserSearchRecord;
import com.Chronova.ChronovaStore.services.CartService;
import com.Chronova.ChronovaStore.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    UserService userService;
    CartService cartService;
    public UserController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }
    

    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("get/user")
    public List<UserRequestDTO> getUsers() {
        return    userService.getUsers();
    }

    @PreAuthorize("hasRole('ADMIN') or #user_id == authentication.principal.id")
    @GetMapping("search/user/{user_id}")
    public UserRequestDTO getUserById(@PathVariable  Integer user_id) {
        return    userService.getUserById(user_id);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("search/users")
    public List<UserRequestDTO> searchUsers(@RequestBody UserSearchRecord filter) {
        return userService.searchUsers(filter);
    }

    @PreAuthorize("hasRole('ADMIN') or #user_id == authentication.principal.id")
    @GetMapping("/delete/user/{user_id}")
    public Boolean deleteUser(@PathVariable  Integer user_id) {
       return  userService.deleteUser(user_id);
    }



}
