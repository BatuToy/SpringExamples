package com.batu.book_network.repositories;

import java.util.Optional;

import com.batu.book_network.entites.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long>{
    Optional<Token> findByToken(String token);
}
