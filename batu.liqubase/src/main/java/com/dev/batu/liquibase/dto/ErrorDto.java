package com.dev.batu.liquibase.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
    @NotNull
    private final Integer code;
    @NotNull
    private final String message;
}
