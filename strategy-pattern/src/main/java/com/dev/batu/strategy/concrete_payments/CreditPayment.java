package com.dev.batu.strategy.concrete_payments;

import com.dev.batu.strategy.PaymentStrategy;
import com.dev.batu.strategy.dto.AccountBalanceDto;
import com.dev.batu.strategy.dto.CommitPaymentDto;
import com.dev.batu.strategy.dto.RollbackPaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/*
 * @created 25/05/2025 ~~ 21:00
 * author: batu
 */
@Slf4j
@Component("credit")
public class CreditPayment implements PaymentStrategy {

    @Override
    public AccountBalanceDto commit(CommitPaymentDto dto) {
        return null;
    }

    @Override
    public AccountBalanceDto rollback(RollbackPaymentDto dto) {
        return null;
    }

}
