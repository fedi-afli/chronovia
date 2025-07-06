package com.Chronova.ChronovaStore.services;

import com.Chronova.ChronovaStore.dataDTO.CartLignRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.CartRequestDTO;
import com.Chronova.ChronovaStore.dataDTO.UserRequestDTO;
import com.Chronova.ChronovaStore.models.User;
import com.Chronova.ChronovaStore.repository.UserRepository;

import java.util.List;

public class UserService {

    private final UserRepository userRepository;
    private final WatchService watchService;
    public UserService(UserRepository userRepository, WatchService watchService) {
        this.userRepository = userRepository;
        this.watchService = watchService;
    }

    public void saveUser(User userDTO) {
          userRepository.save(userDTO);
    }


}
