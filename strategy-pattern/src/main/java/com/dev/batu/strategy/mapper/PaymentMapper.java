package com.dev.batu.strategy.mapper;

/*
 * @created 27/05/2025 ~~ 22:18
 * author: batu
 */

import com.dev.batu.strategy.dto.projection.ProcessPaymentDto;
import com.dev.batu.strategy.model.Account;
import com.dev.batu.strategy.model.PaymentType;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PaymentMapper {

    public static ProcessPaymentDto toPaymentDto(Account sender, Account receiver, BigDecimal transferAmount, String paymentType) {
        return new ProcessPaymentDto(sender, receiver, transferAmount, paymentType);
    }
}
