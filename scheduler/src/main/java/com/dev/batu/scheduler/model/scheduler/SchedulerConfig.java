package com.dev.batu.scheduler.model.scheduler;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/*
 * @created 25/05/2025 ~~ 11:39
 * author: batu
 */
@Entity(name = "scheduler_config")
@Table(name = "t_scheduler_conf", indexes = {
        @Index(name = "idx_methodName", columnList = "methodName")
})
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SchedulerConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column
    private UUID id;

    @Column(nullable = false)
    private String beanName;

    @Column(nullable = false)
    private String methodName;

    @Column
    @Setter
    private String cronExpression;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

    @PrePersist
    public void onPrePersist(){
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = null;
    }

    @PreUpdate
    public void onPreUpdate(){
        this.modifiedAt = LocalDateTime.now();
    }

}
