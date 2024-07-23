package com.batu.book_network.repositories;

import java.util.List;
import java.util.Optional;

import com.batu.book_network.entites.Role;
import com.batu.book_network.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
