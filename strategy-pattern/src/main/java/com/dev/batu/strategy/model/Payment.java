package com.dev.batu.strategy.model;

/*
 * @created 25/05/2025 ~~ 21:10
 * author: batu
 */

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.util.UUID;

@Entity(name = "payment_transaction")
@Table(name = "t_payment_transaction", indexes =  {
        @Index( name = "idx_sender_account_number", columnList = "SENDER_ACCOUNT_NUMBER"),
        @Index( name = "idx_receiver_account_number", columnList = "RECEIVER_ACCOUNT_NUMBER")
})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "PAYMENT_AMOUNT", updatable = false, nullable = false)
    @DecimalMin(value = "00.00")
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ACCOUNT_NUMBER",
            referencedColumnName = "ACCOUNT_NUMBER",
            updatable = false, nullable = false)
    private Account senderAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RECEIVER_ACCOUNT_NUMBER",
            referencedColumnName = "ACCOUNT_NUMBER",
            updatable = false, nullable = false)
    private Account receiverAccount;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "TRANSACTION_STATUS")
    private Status transactionStatus;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "PAYMENT_TYPE")
    private PaymentType paymentType;

    @PrePersist
    public void onPrePersist(){
        if(this.transactionStatus == null){
            this.transactionStatus = Status.PENDING;
        }
    }

}
