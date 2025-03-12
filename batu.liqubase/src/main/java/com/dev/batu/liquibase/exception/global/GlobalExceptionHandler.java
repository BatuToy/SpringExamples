package com.dev.batu.liquibase.exception.global;

import com.dev.batu.liquibase.dto.ErrorDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Log LOGGER = LogFactory.getLog(GlobalExceptionHandler.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler( value = {Exception.class})
    public ResponseEntity<ErrorDto> handle(Exception exception){
        final String errMsg = exception.getLocalizedMessage();
        LOGGER.error(errMsg, exception);
        return ResponseEntity.internalServerError().body(
            new ErrorDto(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    errMsg
            )
        );
    }

     // Exception segregation must be implemented no need for "instance of" usage with if-else statements!
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( value = {ValidationException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handle(ValidationException exception){
        final String errMsg = exception.getLocalizedMessage();
        LOGGER.error(errMsg, exception);
        return ResponseEntity.badRequest().body(
                new ErrorDto(
                        HttpStatus.BAD_REQUEST.value(),
                        errMsg
                )
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler( value = {ConstraintViolationException.class})
    @ResponseBody
    public ResponseEntity<ErrorDto> handle(ConstraintViolationException exception){
            LOGGER.error(exception.getLocalizedMessage(), exception);
            final String errMsg = extractViolations(exception);
            return ResponseEntity.badRequest().body(
                    new ErrorDto(
                            HttpStatus.BAD_REQUEST.value(),
                            errMsg
                    )
            );
    }

    private String extractViolations(ConstraintViolationException exception) {
        return exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.joining("--"));
    }
}

