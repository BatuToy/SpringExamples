package com.btoy.mapperExample.mapperDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btoy.mapperExample.mapperDemo.model.Job;

public interface JobRepository extends JpaRepository<Job, Long>{
    Job getJobById(Long id);   
}
