package com.dev.batu.liquibase.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "t_foods")
public class Food {

    @Id
    @Column(name = "food_id", nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = "food_name", nullable = false, unique = true)
    private String name;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "expiration_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant expirationDate;
}
