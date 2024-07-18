package com.batu.book_network.repositories;

import java.util.Optional;

import com.batu.book_network.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(String role);
}
