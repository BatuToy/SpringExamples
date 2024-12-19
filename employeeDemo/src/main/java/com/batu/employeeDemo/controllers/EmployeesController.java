package com.batu.employeeDemo.controllers;

import com.batu.employeeDemo.dto.EmployeeRequestDto;
import com.batu.employeeDemo.dto.EmployeeResponseDto;
import com.batu.employeeDemo.dto.UpdateEmployeeRequestDto;
import com.batu.employeeDemo.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class EmployeesController {

    private final EmployeeService employeeService;


    @PostMapping()
    public ResponseEntity<EmployeeResponseDto> createEmployee(@RequestBody EmployeeRequestDto req){
        return ResponseEntity.ok(employeeService.createEmployee(req));
    }
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees(){
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
    @GetMapping(value = "/{id}")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable("id") Long id){
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(@RequestBody UpdateEmployeeRequestDto requestDto,
                                                              @PathVariable("id") Long id){
        return ResponseEntity.ok(employeeService.updateEmployee(requestDto, id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable("id") Long id){
        return ResponseEntity.ok(employeeService.deleteEmployee(id));
    }
}


