package com.dev.batu.liquibase.dao;

import com.dev.batu.liquibase.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodRepository extends JpaRepository<Food, UUID> {
    Optional<Food> getFoodsById(UUID id);
}
