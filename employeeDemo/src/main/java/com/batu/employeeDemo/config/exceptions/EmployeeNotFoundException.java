package com.batu.employeeDemo.config.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class EmployeeNotFoundException extends EntityNotFoundException {

    public EmployeeNotFoundException(String message){
        super(message);
    }
}
