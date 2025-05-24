package com.dev.batu.scheduler.repo;

import com.dev.batu.scheduler.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/*
 * @created 24/05/2025 ~~ 15:08
 * author: batu
 */
@Repository
public interface JobRepository extends JpaRepository<Job, UUID> {

    @Modifying
    @Transactional
    @Query("update job j set j.tryColumn = :tryColumn where j.id = :id")
    void updateTryColumnById(@Param("tryColumn") String tryColumn, @Param("id") UUID id);
}
