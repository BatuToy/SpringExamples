package com.dev.batu.strategy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

/*
 * @created 26/05/2025 ~~ 21:00
 * author: batu
 */
@Entity(name = "account")
@Table(name = "t_account", indexes = {
        @Index(name = "idx_account_account_number", columnList = "ACCOUNT_NUMBER")
})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 20, min = 5, message = "Account number must be between 5 and 20 characters")
    @NotBlank(message = "Account number cannot be blank")
    @Column(name = "ACCOUNT_NUMBER", unique = true, nullable = false, updatable = false)
    private String accountNumber;

    @Column(name = "OWNER", nullable = false, updatable = false)
    @NotBlank(message = "Owner name cannot be blank")
    @Size(max = 100, message = "Owner name cannot exceed 100 characters")
    private String owner;

    @Column(name = "BALANCE")
    @PositiveOrZero
    private BigDecimal balance;

}
