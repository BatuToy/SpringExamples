package com.batu.employeeDemo.config.mapper;

import com.batu.employeeDemo.dto.EmployeeResponseDto;
import com.batu.employeeDemo.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Configuration;

@Mapper
public interface EmployeeMapper {
    EmployeeMapper MAPPER = Mappers.getMapper(EmployeeMapper.class);

    EmployeeResponseDto toEmployeeResponse(Employee employee);
}
