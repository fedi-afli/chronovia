package com.Chronova.ChronovaStore.services;


import com.Chronova.ChronovaStore.dataDTO.UserRequestDTO;

import com.Chronova.ChronovaStore.dataDTO.UserSearchRecord;
import com.Chronova.ChronovaStore.models.Cart;
import com.Chronova.ChronovaStore.models.User;
import com.Chronova.ChronovaStore.models.UserSpecifications;
import com.Chronova.ChronovaStore.repository.CartRepository;
import com.Chronova.ChronovaStore.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public UserService(UserRepository userRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;

    }

    public UserRequestDTO saveUser(UserRequestDTO userDTO) {


        User savedUser = userRepository.save(userDTOtoUser(userDTO));


        Cart cart = new Cart();
        cart.setUser(savedUser);
        cart.setTotal(0.0);
        cart.setCartLigns(List.of());

        cartRepository.save(cart);

        return usertoUserDTO(savedUser);
    }
    public List<UserRequestDTO> getUsers() {
        return userRepository.findAll().stream().map(this::usertoUserDTO).collect(Collectors.toList());
    }
    public List<UserRequestDTO> searchUsers(UserSearchRecord filter) {
        Specification<User> spec = UserSpecifications.withAdvancedFilters(
                filter.username(),
                filter.email()
        );

        return userRepository.findAll(spec).stream()
                .map(this::usertoUserDTO)
                .collect(Collectors.toList());
    }

    public User userDTOtoUser(UserRequestDTO userDTO) {
        return new User(userDTO.password(),userDTO.username(),userDTO.email());
    }
    public UserRequestDTO usertoUserDTO(User user) {
        return new UserRequestDTO(user.getUsername(),user.getEmail(),user.getPassword());
    }
    public UserRequestDTO getUserById(Integer id) {
        return usertoUserDTO( userRepository.findById(id).orElse(null));
    }


}
