package com.batu.book_network.config.exception;


public class OperationNotPermittedException extends RuntimeException{

    public OperationNotPermittedException(String message){
        super(message);
    }
}
