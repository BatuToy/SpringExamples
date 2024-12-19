package com.batu.employeeDemo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeRequestDto {
    private String phone;
    private String state;
    private String department;
    private String address;
}
