package com.dev.batu.liquibase.exception.domain;

public abstract class DomainException extends RuntimeException{
     public DomainException(String message) {
        super(message);
    }
}
