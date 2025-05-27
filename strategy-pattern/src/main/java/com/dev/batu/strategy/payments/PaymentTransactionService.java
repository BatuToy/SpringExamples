package com.dev.batu.strategy.payments;

/*
 * @created 26/05/2025 ~~ 22:23
 * author: batu
 */

import com.dev.batu.strategy.dto.projection.PaymentSummaryProjection;
import com.dev.batu.strategy.dto.projection.TransferTransactionDto;

import java.math.BigDecimal;
import java.util.Optional;

public interface PaymentTransactionService {

    void commitPayment(TransferTransactionDto dto);
    void rollbackPayment();
    Optional<PaymentSummaryProjection> findPaymentWithThreshold(BigDecimal threshold);
}
