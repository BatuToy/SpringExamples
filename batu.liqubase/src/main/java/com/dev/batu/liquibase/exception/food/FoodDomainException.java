package com.dev.batu.liquibase.exception.food;

import com.dev.batu.liquibase.exception.domain.DomainException;

public class FoodDomainException extends DomainException {
    public FoodDomainException(String message) {
        super(message);
    }
}
