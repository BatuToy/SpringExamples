package com.dev.batu.strategy.model;

/*
 * @created 26/05/2025 ~~ 20:58
 * author: batu
 */
public enum Status {
    PENDING("PENDING"), COMMIT("COMMIT"), RETURNED("RETURNED"),  FINISHED("FINISHED"), FAILED("FAILED");

    private String name;

    Status(String name) {
    }

    public String getName() {
        return name;
    }

}
