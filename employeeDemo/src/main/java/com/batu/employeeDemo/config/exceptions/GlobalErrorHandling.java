package com.batu.employeeDemo.config.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Configuration
public class GlobalErrorHandling{
        @ExceptionHandler(EmployeeNotFoundException.class)
        public ResponseEntity<ExceptionResponse> handleEntityException(EmployeeNotFoundException exp){
            return ResponseEntity.status(NOT_FOUND).body(
                    ExceptionResponse.builder()
                            .code(NOT_FOUND)
                            .message(exp.getMessage())
                            .build()
            );
        }
    }
