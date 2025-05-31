package com.dev.batu.strategy.repo;

/*
 * @created 26/05/2025 ~~ 21:34
 * author: batu
 */

import com.dev.batu.strategy.dto.projections.PaymentSummaryProjection;
import com.dev.batu.strategy.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    @Query(value = """
            SELECT 
            sa.accountNumber as senderAccount,
            ra.accountNumber as receiverAccount,
            sa.owner as senderOwner,
            ra.owner as receiverOwner,                        
            pt.amount as transferAmount,
            pt.transactionStatus as transactionStatus,
            pt.paymentType as paymentType,            
            CASE pt.amount > (:threshold + 10000) THEN 'HIGH' ELSE 'NORMAL' as riskLevel 
            FROM payment_transaction AS pt
            LEFT JOIN t_account AS sa ON  pt.sender_account_number = sa.account_number
            LEFT JOIN t_account AS ra ON  pt.sender_account_number = ra.account_number
            WHERE pt.amount > :threshold
            """, nativeQuery = true)
    Optional<PaymentSummaryProjection> findPaymentWithThreshold(@Param("threshold") BigDecimal threshold);

    @Query("""
            SELECT pt
            FROM payment_transaction AS pt
            JOIN FETCH pt.senderAccount
            JOIN FETCH pt.receiverAccount
            WHERE pt.id = :paymentId
            """)
    Optional<Payment> fetchPaymentById(@Param("paymentId") UUID id);
}
