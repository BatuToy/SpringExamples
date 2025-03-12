package com.dev.batu.liquibase.rest;

import com.dev.batu.liquibase.dto.AddFoodRequest;
import com.dev.batu.liquibase.dto.AppResponse;
import com.dev.batu.liquibase.dto.FoodDto;
import com.dev.batu.liquibase.service.FoodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/foods")
public class FoodController {

    private final FoodService foodService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping
    public AppResponse<FoodDto> addFood(@RequestBody AddFoodRequest request) {
        FoodDto food = foodService.createFood(request);
        return new AppResponse<>(food, HttpStatus.ACCEPTED
        );
    }
}
