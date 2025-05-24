package com.dev.batu.liquibase.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class AppResponse<T> {

    private final String err;
    private final HttpStatus status;
    private T data;

//    public AppResponse(String err, HttpStatus status) {
//        this.err = err;
//        this.status = status;
//    }

    public AppResponse(T data, HttpStatus status) {
        this.err = "---";
        this.status = status;
        this.data = data;
    }
}
