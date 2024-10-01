package com.btoy.mapperExample.mapperDemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobTitle;
    private String jobDescription;
    private String companyName;
    private String location;
    private Double salary;
    private LocalDate postedDate;
    private boolean jobStatus;

    @ManyToOne
    private User user;
}
