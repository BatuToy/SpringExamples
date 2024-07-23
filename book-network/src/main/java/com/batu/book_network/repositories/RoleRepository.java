package com.batu.book_network.repositories;

import java.util.Optional;

import com.batu.book_network.entites.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, Long>{
    boolean existsRoleByUserId(@Param("userId") Long userId);
}
