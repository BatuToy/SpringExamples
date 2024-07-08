package com.btoy.mapperExample.mapperDemo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.btoy.mapperExample.mapperDemo.Service.Job.JobService;
import com.btoy.mapperExample.mapperDemo.dto.JobDTO;
import com.btoy.mapperExample.mapperDemo.model.Job;

@RestController
@RequestMapping("/api/v1/job")
public class JobController {
    
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobDTO> addJob(@RequestBody Job job){
        var JobDTO =jobService.addJob(job);
        return new ResponseEntity<>(JobDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getJobs() {
        return new ResponseEntity<>(jobService.getAllJobs(), HttpStatus.OK);
    }

    @GetMapping("/{jobId}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long jobId){
        var jobDTO = jobService.getJobById(jobId);
        return new ResponseEntity<>(jobDTO, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAllJobs(){
        boolean deleted = jobService.deleteJobs();
        if(deleted)
            return new ResponseEntity<>("User deleted!", HttpStatus.CONFLICT);
        else {
            return new ResponseEntity<>("User is not deleted!!", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/job/{jobId}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long jobId){
        boolean deleted = jobService.deleteJobById(jobId);
        if(deleted)
            return new ResponseEntity<>("Id with" + " " + jobId +" is deleted!", HttpStatus.CONFLICT);
        else {
            return new ResponseEntity<>("Id with" + " " + jobId +" is NOT deleted!", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{jobId}")
    public ResponseEntity<JobDTO> upadteJob(@PathVariable Long jobId, @RequestBody Job job){
    var jobDTO = jobService.updateJob(jobId, job);
        return new ResponseEntity<>(jobDTO, HttpStatus.NO_CONTENT);
    }
}
