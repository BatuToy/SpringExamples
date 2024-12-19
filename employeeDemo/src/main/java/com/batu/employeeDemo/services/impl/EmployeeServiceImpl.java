package com.batu.employeeDemo.services.impl;

import com.batu.employeeDemo.config.exceptions.EmployeeNotFoundException;
import com.batu.employeeDemo.config.mapper.EmployeeMapper;
import com.batu.employeeDemo.dto.EmployeeRequestDto;
import com.batu.employeeDemo.dto.EmployeeResponseDto;
import com.batu.employeeDemo.dto.UpdateEmployeeRequestDto;
import com.batu.employeeDemo.entity.Employee;
import com.batu.employeeDemo.repository.EmployeeRepository;
import com.batu.employeeDemo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEE_NOT_FOUND = "Employee not found!";
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;


    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto req) {
        Employee addedEmployee = employeeRepository.save(getEmployee(req));
        EmployeeResponseDto response = employeeMapper.toEmployeeResponse(addedEmployee);
        return response;
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toEmployeeResponse).toList();
    }


    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () ->  new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND)
        );
        return EmployeeMapper.MAPPER.toEmployeeResponse(employee);
    }

    @Override
    public EmployeeResponseDto updateEmployee(UpdateEmployeeRequestDto requestDto, Long id) {
        var employee = employeeRepository.findById(id)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND)
                );
        setAndSaveEmployee(employee, requestDto);
        return EmployeeMapper.MAPPER.toEmployeeResponse(employee);
    }

    // why mapper don't see the impl.

    @Override
    public boolean deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow( () -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND));
        if(employee == null){
            return false;
        } else {
            employeeRepository.delete(employee);
            return true;
        }
    }

    private static Employee getEmployee(EmployeeRequestDto req) {
        return Employee.builder()
                .email(req.getEmail())
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .address(req.getAddress())
                .department(req.getDepartment())
                .state(req.getState())
                .phone(req.getPhone())
                .joiningDate(LocalDateTime.now())
                .status(req.getStatus())
                .build();
    }
    private void setAndSaveEmployee(Employee employee, UpdateEmployeeRequestDto requestDto){
        employee.setAddress(requestDto.getAddress());
        employee.setDepartment(requestDto.getDepartment());
        employee.setPhone(requestDto.getPhone());
        employee.setState(requestDto.getState());
        employeeRepository.save(employee);
    }
}
