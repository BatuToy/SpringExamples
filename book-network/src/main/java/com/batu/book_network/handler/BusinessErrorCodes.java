package com.batu.book_network.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum BusinessErrorCodes {
    // Add status codes here
    NO_CODE(0, NOT_IMPLEMENTED, "No code found"),
    INCORRECT_CURRENT_PASSWORD(300, BAD_REQUEST, "Incorrect current password"),
    INCORRECT_NEW_PASSWORD(301, BAD_REQUEST, "Incorrect new password"),
    ACCOUNT_LOCKED(302, FORBIDDEN, "Account is locked"),
    ACCOUNT_DISABLED(303, FORBIDDEN, "Account is disabled"),
    BAD_CREDENTIALS(304, UNAUTHORIZED, "Bad credentials"),
    ;
    private final int code;
    private final String description;
    private final HttpStatus httpStatus;

    BusinessErrorCodes(int code, HttpStatus httpStatus, String description) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.description = description;
    }
}
