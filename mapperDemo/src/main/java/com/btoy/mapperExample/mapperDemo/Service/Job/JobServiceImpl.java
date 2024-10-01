package com.btoy.mapperExample.mapperDemo.Service.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.btoy.mapperExample.mapperDemo.convert.JobDTOConverter;
import com.btoy.mapperExample.mapperDemo.dto.JobDTO;
import com.btoy.mapperExample.mapperDemo.model.Job;
import com.btoy.mapperExample.mapperDemo.repository.JobRepository;

@Service
public class JobServiceImpl implements JobService{

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobDTOConverter jobDTOConverter;

    private final List<JobDTO> jobDTOs = new ArrayList<>();

    @Override
    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        for (Job job : jobs) {
            var jobDTO = jobDTOConverter.ConvertJobToJobDTO(job); 
            jobDTOs.add(jobDTO);
        }
        return jobDTOs;
    }

    @Override
    public JobDTO addJob(Job job) {
        jobRepository.save(job);
        var jobDTO = jobDTOConverter.ConvertJobToJobDTO(job);
        return jobDTO;
    }

    @Override
    public JobDTO getJobById(Long id) {
        var job = jobRepository.getJobById(id);
        var jobDTO = jobDTOConverter.ConvertJobToJobDTO(job);
        return jobDTO;
    }

    @Override
    public boolean deleteJobs() {
            jobRepository.deleteAll();
            return true;
    }

    @Override
    public boolean deleteJobById(Long id) {
        jobRepository.deleteById(id);
        return true;
    }

    @Override
    public JobDTO updateJob(Long id, Job job) {
        Optional<Job> optionalJob = Optional.of(jobRepository.getJobById(id));
        if(optionalJob.isPresent()){
            var jobToUpdate = optionalJob.get();
            jobToUpdate.setJobTitle(job.getJobTitle());
            jobToUpdate.setJobStatus(job.isJobStatus());
            jobToUpdate.setJobDescription(job.getJobDescription());
            jobToUpdate.setCompanyName(job.getCompanyName());
            jobToUpdate.setPostedDate(job.getPostedDate());
            jobToUpdate.setSalary(job.getSalary());
            jobToUpdate.setUser(job.getUser());
            jobRepository.save(jobToUpdate);
            var jobDTO = jobDTOConverter.ConvertJobToJobDTO(jobToUpdate);
            return jobDTO;
        } else {
            throw new RuntimeException("Uppdate failed!");
        }
    }
}