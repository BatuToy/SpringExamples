package com.batu.book_network.role;

public enum Roles {

    ROLE_USER(0, "ROLE_USER"),
    ROLE_ADMIN(1, "ROLE_ADMIN")
    ;
    private Integer code;
    private final String role;

    Roles(Integer code, String role) {
        this.code = code;
        this.role = role;
    }
}
