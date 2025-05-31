package com.dev.batu.strategy.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/*
 * @created 27/05/2025 ~~ 22:07
 * author: batu
 */

@Getter
@AllArgsConstructor
public class TransferTransactionDto {

    private final String senderAccountNumber;
    private final String receiverAccountNumber;
    private final BigDecimal transferAmount;
    private final String paymentType;
}
