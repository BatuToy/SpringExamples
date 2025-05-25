package com.dev.batu.scheduler.rest;

import com.dev.batu.scheduler.dto.scheduler.UpdateCronWithTaskNameDto;
import com.dev.batu.scheduler.service.DynamicSchedulingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * @created 25/05/2025 ~~ 14:27
 * author: batu
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/scheduler")
public class DynamicSchedulingController {

    private final DynamicSchedulingService dynamicSchedulingService;

    @PostMapping(value = "/updateCron")
    public ResponseEntity<Void> updateCronExpression(@RequestBody UpdateCronWithTaskNameDto dto){
        dynamicSchedulingService.updateCronExpressionOfJobWithTaskName(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
