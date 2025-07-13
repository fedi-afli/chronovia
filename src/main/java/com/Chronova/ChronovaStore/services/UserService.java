package com.Chronova.ChronovaStore.services;


import com.Chronova.ChronovaStore.dataDTO.UserRequestDTO;

import com.Chronova.ChronovaStore.dataDTO.UserSearchRecord;
import com.Chronova.ChronovaStore.models.Cart;
import com.Chronova.ChronovaStore.models.User;
import com.Chronova.ChronovaStore.models.UserSpecifications;
import com.Chronova.ChronovaStore.repository.CartRepository;
import com.Chronova.ChronovaStore.repository.UserRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, CartRepository cartRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public UserRequestDTO saveUser(UserRequestDTO userDTO) {

    // Note: User creation is now handled by AuthService for proper security
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

    
    public UserRequestDTO usertoUserDTO(User user) {
        return new UserRequestDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().name(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getLastLoginAt()
        );
    }
    
    public UserRequestDTO getUserById(Integer id) {
        return usertoUserDTO( userRepository.findById(id).orElse(null));
    }


    public Boolean deleteUser(Integer userId) {
         userRepository.deleteById(userId);
         return true;
    }
}
