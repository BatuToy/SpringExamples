package com.batu.book_network.repositories;

import com.batu.book_network.entites.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
            SELECT t
            FROM Token t
            INNER JOIN User u ON t.user.id = u.id
            WHERE u.id = :userId AND (t.expired = false OR t.revoked = false)
            """)
    List<Token> findAllValidTokensByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT token
            FROM Token token
            WHERE token.token = :jwt
            """)
    Optional<Token> findByToken(@Param("jwt") String token);

}
