package com.dev.batu.scheduler.service;

import com.dev.batu.scheduler.dto.PersistJobDto;
import com.dev.batu.scheduler.model.Job;
import com.dev.batu.scheduler.repo.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/*
 * @created 24/05/2025 ~~ 15:11
 * author: batu
 */
@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService{

    private static final String UPDATE_MARKER = "--";
    private final JobRepository jobRepository;

    @Override
    public void updateTryColumn() {
        jobRepository.findAll().forEach(job  -> {
             jobRepository.updateTryColumnById(job.getTryColumn() + UPDATE_MARKER, job.getId());
        });
    }

    @Override
    public void deleteAll() {
        jobRepository.deleteAll();
    }

    @Override
    public PersistJobDto persistJob(String tryColumn) {
        Job job = Job.builder()
                .tryColumn(tryColumn)
                .build();
        return new PersistJobDto("Job with job id= "+ job.getId().toString()
                + "persisted successfully in to the persist!");
    }
}
