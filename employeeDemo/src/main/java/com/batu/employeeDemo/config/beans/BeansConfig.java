package com.batu.employeeDemo.config.beans;

import com.batu.employeeDemo.config.mapper.EmployeeMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public EmployeeMapper employeeMapper(){
        return EmployeeMapper.MAPPER;
    }
}
