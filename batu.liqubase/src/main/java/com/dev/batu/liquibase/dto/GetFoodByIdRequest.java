package com.dev.batu.liquibase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class GetFoodByIdRequest {
    private final UUID foodId;
}
