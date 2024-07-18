package com.batu.book_network.services;

import com.batu.book_network.entites.Token;
import com.batu.book_network.entites.User;
import com.batu.book_network.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(User user) {
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

    public User getUserByToken(Token token){
        return userRepository.findById(token.getUser().getId()).orElseThrow(() -> new IllegalStateException("User not found"));
    }
}
