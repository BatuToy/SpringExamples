package com.batu.book_network.repositories;

import java.util.Optional;

import com.batu.book_network.entites.Activation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivationRepository extends JpaRepository<Activation, Long>{
    Optional<Activation> findByActivationCode(String token);
}
