package com.dev.batu.strategy.dto.projections;

import java.math.BigDecimal;

/*
 * @created 26/05/2025 ~~ 21:57
 * author: batu
 */
public interface PaymentSummaryProjection {
    String getSenderAccountNumber();
    String getReceiverAccountNumber();
    String getSenderOwner();
    String getReceiverOwner();
    BigDecimal getTransferAmount();
    String getTransactionStatus();
    String getPaymentType();
    String getRiskLevel();
}
