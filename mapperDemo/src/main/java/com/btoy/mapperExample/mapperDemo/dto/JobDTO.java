package com.btoy.mapperExample.mapperDemo.dto;
 
import com.btoy.mapperExample.mapperDemo.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDTO {
    private Long id;
    private String jobTitle;
    private String jobDescription;
    private String companyName;

    private User user;
}
