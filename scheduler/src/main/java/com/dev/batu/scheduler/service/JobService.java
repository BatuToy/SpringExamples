package com.dev.batu.scheduler.service;

import com.dev.batu.scheduler.model.Job;

/*
 * @created 24/05/2025 ~~ 15:07
 * author: batu
 */
public interface JobService {

    void updateTryColumn();
    void deleteAll();
    Job persistJob(Job job);

}
