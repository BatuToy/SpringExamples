package com.dev.batu.liquibase;

import com.dev.batu.liquibase.dto.AddFoodRequest;
import com.dev.batu.liquibase.dto.FoodDto;
import com.dev.batu.liquibase.dto.GetFoodByIdRequest;
import com.dev.batu.liquibase.model.Food;
import com.dev.batu.liquibase.service.FoodService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationTests {
	@Autowired
	private FoodService foodService;

	private static final UUID FOOD_ID = UUID.fromString("00a98a8d-4a44-4c9b-bae4-69d9126471e6");

	@Test
	void createFood() {
		AddFoodRequest request = new AddFoodRequest(
				"apple",
				10,
				1.48,
				45L
		);
		FoodDto response = foodService.createFood(request);
		assertEquals(request.getWeight(), response.getWeight());
	}

	@Test
	void getFoodByFoodId() {
		GetFoodByIdRequest request = new GetFoodByIdRequest(
			FOOD_ID
		);
		FoodDto response = foodService.getFoodByFoodId(request);
		assertEquals(response.getFoodId(), request.getFoodId());
	}

}
