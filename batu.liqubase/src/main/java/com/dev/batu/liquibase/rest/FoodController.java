package com.dev.batu.liquibase.rest;

import com.dev.batu.liquibase.dto.AddFoodRequest;
import com.dev.batu.liquibase.dto.AppResponse;
import com.dev.batu.liquibase.dto.FoodDto;
import com.dev.batu.liquibase.dto.GetFoodByIdRequest;
import com.dev.batu.liquibase.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/foods")
public class FoodController {

    private static final Logger LOGGER = Logger.getLogger( FoodController.class.getName() );

    private final FoodService foodService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public AppResponse<FoodDto> addFood(@RequestBody AddFoodRequest request) {
        LOGGER.log(Level.INFO, "Food starting to creating. Current class={0}", FoodController.class);
        return new AppResponse<>(
                foodService.createFood(request),
                HttpStatus.ACCEPTED
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = "/get")
    public AppResponse<FoodDto> getFoodByFoodId(@RequestBody GetFoodByIdRequest request) {
        LOGGER.log(Level.INFO, "All foods are starting to list. Current class={0}", FoodController.class);
        return new AppResponse<>(
            foodService.getFoodByFoodId(request),
                HttpStatus.OK
        );
    }
}
