package com.batu.book_network.impl;

import com.batu.book_network.entites.Activation;
import com.batu.book_network.entites.User;
import com.batu.book_network.repositories.UserRepository;
import com.batu.book_network.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public void existByEmail(String email) {
        if(userRepository.existsByEmail(email)){
            throw new IllegalStateException("Email is already taken!");
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalStateException("User not found"));
    }

    public User getUserByActivationCode(Activation activation){
        return userRepository.findById(activation.getUser().getId()).orElseThrow(() -> new IllegalStateException("User not found"));
    }
}
