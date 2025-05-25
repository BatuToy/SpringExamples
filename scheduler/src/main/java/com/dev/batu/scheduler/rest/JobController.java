package com.dev.batu.scheduler.rest;

import com.dev.batu.scheduler.dto.PersistJobDto;
import com.dev.batu.scheduler.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 * @created 25/05/2025 ~~ 10:47
 * author: batu
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/job")
public class JobController {

    private final JobService jobService;

    @PostMapping
    public ResponseEntity<PersistJobDto> createJob(@RequestParam("tryColumn") String tryColumn){
        PersistJobDto dto = jobService.persistJob(tryColumn);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

}
