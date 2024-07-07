package com.btoy.mapperExample.mapperDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btoy.mapperExample.mapperDemo.model.User;

public interface UserRepository extends JpaRepository<User, Long>{
    User getUserById(Long userId);
}
