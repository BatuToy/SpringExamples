package com.dev.batu.strategy.dto;

import com.dev.batu.strategy.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/*
 * @created 27/05/2025 ~~ 23:00
 * author: batu
 */

@Getter
public class AccountBalanceDto {

    private final BigDecimal senderBalance;
    private final BigDecimal receiverBalance;

    private Payment payment;

    public AccountBalanceDto(BigDecimal senderBalance, BigDecimal receiverBalance, Payment payment) {
        this.senderBalance = senderBalance;
        this.receiverBalance = receiverBalance;
        this.payment = payment;
    }

    public AccountBalanceDto(BigDecimal senderBalance, BigDecimal receiverBalance) {
        this.senderBalance = senderBalance;
        this.receiverBalance = receiverBalance;
    }
}
