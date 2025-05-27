package com.dev.batu.strategy.exception;

/*
 * @created 27/05/2025 ~~ 23:06
 * author: batu
 */
public class PaymentApiException extends RuntimeException{
    public PaymentApiException(String message) {
        super(message);
    }

    public PaymentApiException() {
    }
}
