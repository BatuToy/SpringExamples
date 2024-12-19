package com.batu.employeeDemo.services;


import com.batu.employeeDemo.dto.EmployeeRequestDto;
import com.batu.employeeDemo.dto.EmployeeResponseDto;
import com.batu.employeeDemo.dto.UpdateEmployeeRequestDto;

import java.util.List;

public interface EmployeeService {
    EmployeeResponseDto createEmployee(EmployeeRequestDto employeeRequestDto);
    List<EmployeeResponseDto> getAllEmployees();
    EmployeeResponseDto getEmployeeById(Long id);
    EmployeeResponseDto updateEmployee(UpdateEmployeeRequestDto requestDto, Long id);
    boolean deleteEmployee(Long id);
}
