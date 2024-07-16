package com.btoy.mapperExample.mapperDemo.convert;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.btoy.mapperExample.mapperDemo.dto.JobDTO;
import com.btoy.mapperExample.mapperDemo.model.Job;

@Component
public class JobDTOConverter {
    @Autowired
    private ModelMapper modelMapper;

    public JobDTO ConvertJobToJobDTO(Job job){

        JobDTO jobDTO = modelMapper.map(job, JobDTO.class);

        return jobDTO;
    }

    public Job ConvertJobDTOToJOb(JobDTO jobDTO){

        Job job = modelMapper.map(jobDTO, Job.class);

        return job;
    }
}
