package com.batu.employeeDemo.enums;

import lombok.Getter;

public enum EmployeeStatus {
    ACTIVE("WORKING"),
    INACTIVE("WAITING")
    ;
    private final String status;

    EmployeeStatus(String status){
        this.status = status;
    }
}
