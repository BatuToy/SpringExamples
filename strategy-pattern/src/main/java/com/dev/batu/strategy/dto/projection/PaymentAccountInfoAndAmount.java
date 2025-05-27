package com.dev.batu.strategy.dto.projection;

import java.math.BigDecimal;

/*
 * @created 26/05/2025 ~~ 21:35
 * author: batu
 */
public interface PaymentAccountInfoAndAmount {

    String getSenderAccountNumber();
    String getReceiverAccountNumber();
    String getTransactionStatus();
    BigDecimal getTransactionAmount();

}
