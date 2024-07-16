package com.batu.book_network.handler;

import com.batu.book_network.exception.OperationNotPermittedException;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException lockedException){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse
                        .builder()
                        .businessErrorCode(BusinessErrorCodes.ACCOUNT_LOCKED.getCode())
                        .businessExceptionDescription(BusinessErrorCodes.ACCOUNT_LOCKED.getDescription())
                        .error(lockedException.getMessage())
                        .build());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ExceptionResponse> handleException(DisabledException disabledException){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(ExceptionResponse
                        .builder()
                        .businessErrorCode(BusinessErrorCodes.ACCOUNT_DISABLED.getCode())
                        .businessExceptionDescription(BusinessErrorCodes.ACCOUNT_DISABLED.getDescription())
                        .error(disabledException.getMessage())
                        .build());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException(){
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse
                                .builder()
                                .businessErrorCode(BusinessErrorCodes.BAD_CREDENTIALS.getCode())
                                .businessExceptionDescription(BusinessErrorCodes.BAD_CREDENTIALS.getDescription())
                                .error("Login or/and password is incorrect")
                                .build()
                );
    }
    // Validation error handling
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp){
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var validationError = error.getDefaultMessage();
                    errors.add(validationError);
                });
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(ExceptionResponse
                        .builder()
                        .validationErrors(errors)
                        .build());
    }

    @ExceptionHandler(MessagingException.class)
    public ResponseEntity<ExceptionResponse> handleException(MessagingException exp){
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse
                                .builder()
                                .error(exp.getMessage())
                                .build()
                );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp){
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse
                                .builder()
                                .businessExceptionDescription("Internal server error, please contact admin!")
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    public ResponseEntity<ExceptionResponse> handleException(OperationNotPermittedException exp){
        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse
                                .builder()
                                .error(exp.getMessage())
                                .build()
                );
    }
}
