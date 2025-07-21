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


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    // You can complete this method if you want to create/save a user
    public UserRequestDTO saveUser(UserRequestDTO userDTO) {
        // Normally you’d map userDTO to a User entity, encode password, etc.
        // Not implemented here as the comment says AuthService handles it
        return null;
    }

    public List<UserRequestDTO> getUsers() {
        return userRepository.findAll().stream()
                .map(this::usertoUserDTO)
                .collect(Collectors.toList());
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
        return userRepository.findById(id)
                .map(this::usertoUserDTO)
                .orElse(null);
    }

    public Boolean deleteUser(Integer userId) {
        userRepository.deleteById(userId);
        return true;
    }
}
