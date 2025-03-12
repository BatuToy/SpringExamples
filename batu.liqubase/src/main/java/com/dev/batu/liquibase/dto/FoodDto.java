package com.dev.batu.liquibase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class FoodDto {

    @NotNull
    private final UUID foodId;
}
