package com.batu.book_network.entites.enums;

import lombok.Getter;

@Getter
public enum Roles {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN")
    ;
    private final String role;

    Roles(String role) {
        this.role = role;
    }
}
