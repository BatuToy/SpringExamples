package com.dev.batu.strategy.model;

/*
 * @created 26/05/2025 ~~ 22:10
 * author: batu
 */
public enum PaymentType {
    CREDIT("CREDIT"), CRYPTO("CRYPTO"), PAYPAL("PAYPAL");

    private final String name;

    PaymentType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
