package com.dev.batu.strategy.service;

/*
 * @created 26/05/2025 ~~ 22:23
 * author: batu
 */

import com.dev.batu.strategy.dto.projections.PaymentSummaryProjection;
import com.dev.batu.strategy.dto.ReturnPaymentDto;
import com.dev.batu.strategy.dto.TransferTransactionDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface PaymentTransactionService {

    void commitPayment(TransferTransactionDto dto);
    void returnPayment(ReturnPaymentDto dto);
    Optional<PaymentSummaryProjection> findPaymentWithThreshold(BigDecimal threshold);
}
