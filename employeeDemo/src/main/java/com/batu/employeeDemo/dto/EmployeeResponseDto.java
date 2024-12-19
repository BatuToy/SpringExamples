package com.batu.employeeDemo.dto;

import com.batu.employeeDemo.enums.EmployeeStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String department;
    private String state;
    private LocalDateTime joiningDate;
    private EmployeeStatus status;
}
