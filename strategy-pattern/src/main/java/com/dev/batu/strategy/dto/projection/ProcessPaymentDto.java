package com.dev.batu.strategy.dto.projection;

/*
 * @created 27/05/2025 ~~ 22:19
 * author: batu
 */

import com.dev.batu.strategy.model.Account;
import com.dev.batu.strategy.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class ProcessPaymentDto {

    private final Account sender;
    private final Account receiver;
    private final BigDecimal transferAmount;
    private final String paymentType;
}
