package com.dev.batu.scheduler.service;

import com.dev.batu.scheduler.dto.PersistJobDto;
import com.dev.batu.scheduler.model.Job;
import com.dev.batu.scheduler.repo.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/*
 * @created 24/05/2025 ~~ 15:11
 * author: batu
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService{

    private static final String UPDATE_MARKER = "--";
    private final JobRepository jobRepository;


    @Override
    public void updateTryColumn() {
        jobRepository.findAll().forEach(job  -> {
             jobRepository.updateTryColumnById(job.getTryColumn() + UPDATE_MARKER, job.getId());
             log.info("Try Column updated with value= {}", job.getTryColumn());
        });
    }

    @Override
    public void deleteAll() {
        jobRepository.deleteAll();
    }

    @Override
    public Job findById(String jobId){
        return jobRepository.findById(UUID.fromString(jobId)).orElseThrow(() -> new EntityNotFoundException(""));
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public PersistJobDto persistJob(String tryColumn) {
        Job job = Job.builder()
                .tryColumn(tryColumn)
                .build();
        jobRepository.save(job);
        return new PersistJobDto("Job with job id= "+ job.getId().toString()
                + "persisted successfully in to the persist!");
    }
}
