package com.dev.batu.strategy.dto;

/*
 * @created 27/05/2025 ~~ 22:19
 * author: batu
 */

import com.dev.batu.strategy.model.Account;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CommitPaymentDto {

    private final Account sender;
    private final Account receiver;
    private final BigDecimal transferAmount;
    private final String paymentType;

    public CommitPaymentDto(Account sender, Account receiver, BigDecimal transferAmount, String paymentType) {
        this.sender = sender;
        this.receiver = receiver;
        this.transferAmount = transferAmount;
        this.paymentType = paymentType;
    }
}
