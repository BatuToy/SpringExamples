package com.dev.batu.scheduler.repo.scheduler;

import com.dev.batu.scheduler.model.scheduler.SchedulerConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/*
 * @created 25/05/2025 ~~ 11:45
 * author: batu
 */
@Repository
public interface SchedulerConfigRepository extends JpaRepository<SchedulerConfig, UUID> {
    Optional<SchedulerConfig> findByMethodName(String methodName);
}
