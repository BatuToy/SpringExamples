package com.dev.batu.strategy.mapper;

/*
 * @created 27/05/2025 ~~ 22:18
 * author: batu
 */

import com.dev.batu.strategy.dto.CommitPaymentDto;
import com.dev.batu.strategy.dto.RollbackPaymentDto;
import com.dev.batu.strategy.model.Account;
import com.dev.batu.strategy.model.Payment;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PaymentMapper {

    public static CommitPaymentDto toCommitPaymentDto(Account sender, Account receiver, BigDecimal transferAmount, String paymentType) {
        return new CommitPaymentDto(sender, receiver, transferAmount, paymentType);
    }

    public static RollbackPaymentDto toRollbackPaymentDto(Account sender, Account receiver, Payment payment) {
        return new RollbackPaymentDto(sender, receiver, payment);
    }
}
