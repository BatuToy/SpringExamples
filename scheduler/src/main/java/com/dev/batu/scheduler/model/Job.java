package com.dev.batu.scheduler.model;

/*
 * @created 24/05/2025 ~~ 13:15
 * author: batu
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;
import java.util.UUID;

@Builder
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
    private String jobName;

    @Column(nullable = false)
    private String tryColumn;

    @PrePersist
    public void onPrePersist(){
        this.jobName = UUID.randomUUID().toString().substring(3, 11).toLowerCase(Locale.ROOT);
        this.id = UUID.randomUUID();
    }


}
