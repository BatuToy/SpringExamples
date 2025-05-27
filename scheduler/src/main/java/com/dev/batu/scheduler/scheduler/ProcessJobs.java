package com.dev.batu.scheduler.scheduler;

import com.dev.batu.scheduler.repo.JobRepository;
import com.dev.batu.scheduler.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * @created 24/05/2025 ~~ 13:20
 * author: batu
 */
@Slf4j
@RequiredArgsConstructor
@Component("scheduler") // Have to mark as a Spring bean !
public class ProcessJobs {

    /* cron, string or millisecond as long time vars . */

    private final JobService service;

    // Every minute !
    // todo : updateTryColumn with 100000 data
    //@Scheduled(cron = "0 * * * * *")
    public void updateAllTryColumns(){
        log.info("Updated for 100000 rows on the table Jobs starting !");
        service.updateTryColumn();
        log.info("Updated for 100000 rows on the table Jobs finished !");
    }

    // Every six hours !
    //@Scheduled(cron = "0 0 */6 * * *")
    public void deleteAll(){
        log.info("Deleting all the jobs !");
        service.deleteAll();
    }

}
