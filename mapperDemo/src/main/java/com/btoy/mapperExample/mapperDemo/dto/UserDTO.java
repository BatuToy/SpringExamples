package com.btoy.mapperExample.mapperDemo.dto;

import java.util.List;

import com.btoy.mapperExample.mapperDemo.model.Job;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;

    private List<Job> jobs;
}
