package com.dev.batu.strategy.dto.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

/*
 * @created 27/05/2025 ~~ 23:00
 * author: batu
 */
@AllArgsConstructor
@Getter
public class AccountBalanceDto {

    private final BigDecimal senderBalance;
    private final BigDecimal receiverBalance;
}
