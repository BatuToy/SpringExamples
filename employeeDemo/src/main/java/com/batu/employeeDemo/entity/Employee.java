package com.batu.employeeDemo.entity;

import com.batu.employeeDemo.enums.EmployeeStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String address;
    private String phone;
    private String department;
    private String state;
    private LocalDateTime joiningDate;
    private EmployeeStatus status;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;
}
