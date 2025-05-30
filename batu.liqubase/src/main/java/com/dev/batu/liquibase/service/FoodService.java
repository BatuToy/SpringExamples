package com.dev.batu.liquibase.service;

import com.dev.batu.liquibase.dto.AddFoodRequest;
import com.dev.batu.liquibase.dto.FoodDto;
import com.dev.batu.liquibase.dto.GetFoodByIdRequest;
import jakarta.validation.Valid;


public interface FoodService {
    FoodDto createFood(@Valid AddFoodRequest request);

    FoodDto getFoodByFoodId(@Valid GetFoodByIdRequest request);
}
