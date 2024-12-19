package com.batu.employeeDemo.dto;

import com.batu.employeeDemo.enums.EmployeeStatus;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequestDto {

    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String department;
    private String state;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime joiningDate;
    private EmployeeStatus status;
}
