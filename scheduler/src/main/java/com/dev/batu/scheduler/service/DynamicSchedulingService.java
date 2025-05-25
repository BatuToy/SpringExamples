package com.dev.batu.scheduler.service;

import com.dev.batu.scheduler.dto.scheduler.UpdateCronWithTaskNameDto;
import com.dev.batu.scheduler.model.scheduler.SchedulerConfig;
import com.dev.batu.scheduler.repo.scheduler.SchedulerConfigRepository;
import com.dev.batu.scheduler.scheduler.ProcessJobs;
import jakarta.el.MethodNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.SimpleAsyncTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/*
 * @created 25/05/2025 ~~ 11:36
 * author: batu
 */

@Slf4j
@Service
public class DynamicSchedulingService {

    @Autowired
    private final ApplicationContext ctx;
    private final SchedulerConfigRepository schedulerConfigRepository;
    private final ThreadPoolTaskScheduler taskScheduler;
    private ConcurrentHashMap<String, ScheduledFuture<?>> future = new ConcurrentHashMap<>();

    public DynamicSchedulingService(SchedulerConfigRepository schedulerConfigRepository, ThreadPoolTaskScheduler taskScheduler, ApplicationContext ctx) {
        this.taskScheduler = taskScheduler;
        taskScheduler.initialize();
        this.schedulerConfigRepository = schedulerConfigRepository;
        this.ctx = ctx;
    }

    public void updateCronExpressionOfJobWithTaskName(UpdateCronWithTaskNameDto dto){
        SchedulerConfig schedulerConfig = schedulerConfigRepository.findByMethodName(dto.getMethodName())
                .orElse(SchedulerConfig.builder()
                        .methodName(dto.getMethodName())
                        .beanName(dto.getBeanName())
                        .cronExpression(dto.getCronExpression())
                        .build());
        schedulerConfig.setCronExpression(dto.getCronExpression());
        schedulerConfigRepository.save(schedulerConfig);
        restartTask(schedulerConfig);
    }

    private void restartTask(SchedulerConfig config){
        final String taskIdentifier = config.getBeanName() + "#" + config.getMethodName();
        Optional.ofNullable(future.get(taskIdentifier)).ifPresent(f -> f.cancel(false));
        Object bean = ctx.getBean(config.getBeanName());
        Method method = ReflectionUtils.findMethod(bean.getClass(), config.getMethodName());
        checkMethod(method);
        Runnable task = () -> ReflectionUtils.invokeMethod(method, bean);
        future.put(taskIdentifier, Objects.requireNonNull(taskScheduler.schedule(task, new CronTrigger(config.getCronExpression()))));
    }

    private void checkMethod(Method m){
        if(Objects.isNull(m)) throw new MethodNotFoundException("");
    }
}
