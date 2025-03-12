package com.dev.batu.liquibase.service;

import com.dev.batu.liquibase.dao.FoodRepository;
import com.dev.batu.liquibase.dto.AddFoodRequest;
import com.dev.batu.liquibase.dto.FoodDto;
import com.dev.batu.liquibase.exception.food.FoodDomainException;
import com.dev.batu.liquibase.model.Food;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Validated
@Service
public class FoodServiceImpl implements FoodService {

    private static final Log LOGGER = LogFactory.getLog(FoodServiceImpl.class);

    private final FoodRepository foodRepository;

    public FoodServiceImpl(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public FoodDto createFood(@Valid AddFoodRequest request) {
        try{
            Food food = foodRepository.save(new Food(
                    UUID.randomUUID(),
                    request.getFoodName(),
                    request.getQuantity(),
                    request.getWeight(),
                    Instant.now().plus(request.getExpiredTime(), ChronoUnit.DAYS)
            ));
            return new FoodDto(food.getId());
        } catch ( FoodDomainException foodDomainException) {
            LOGGER.error("An error occur while persisting food in to the database!",
                    foodDomainException);
            throw new FoodDomainException("An error occur while persisting food in to the database!");
        }
    }
}
