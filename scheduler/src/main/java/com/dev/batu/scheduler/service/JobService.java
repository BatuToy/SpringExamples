package com.dev.batu.scheduler.service;

import com.dev.batu.scheduler.dto.PersistJobDto;
import com.dev.batu.scheduler.model.Job;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

import java.util.List;

/*
 * @created 24/05/2025 ~~ 15:07
 * author: batu
 */
public interface JobService {

    void updateTryColumn();
    void deleteAll();
    Job findById(String jobId);
    List<Job> findAll();
    PersistJobDto persistJob(String tryColumn);

}
