package com.dev.batu.liquibase.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class AddFoodRequest {

    @NotNull
    @NotEmpty
    @Size(min = 3, message = "foodName must be greater then 3 characters!")
    private String foodName;
    @NotNull
    @Min(1)
    private final Integer quantity;
    @NotNull
    private final Double weight;
    @NotNull
    private final Long expiredTime;

}
