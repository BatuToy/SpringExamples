package com.btoy.mapperExample.mapperDemo.Service.Job;

import java.util.List;

import org.springframework.stereotype.Service;

import com.btoy.mapperExample.mapperDemo.dto.JobDTO;
import com.btoy.mapperExample.mapperDemo.model.Job;

@Service
public interface JobService {
    List<JobDTO> getAllJobs();
    JobDTO getJobById(Long id);
    JobDTO addJob(Job job);
    boolean deleteJobs();
    boolean deleteJobById(Long id);
    JobDTO updateJob(Long id, Job job);
}