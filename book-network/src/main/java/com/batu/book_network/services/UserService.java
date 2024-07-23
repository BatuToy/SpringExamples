package com.batu.book_network.services;

import com.batu.book_network.entites.Activation;
import com.batu.book_network.entites.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService  {
    User save(User user);
    void existByEmail(String email);
    User getUserById(Long userId);
    User getUserByActivationCode(Activation activation);
}
