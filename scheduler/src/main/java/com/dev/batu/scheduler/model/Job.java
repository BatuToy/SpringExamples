package com.dev.batu.scheduler.model;

/*
 * @created 24/05/2025 ~~ 13:15
 * author: batu
 */

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "job")
@Table(name = "t_jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {

    @Id
    @Column
    private UUID id;

    @Column(nullable = false, updatable = false, unique = true)
    private String jobName = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String tryColumn;

}
