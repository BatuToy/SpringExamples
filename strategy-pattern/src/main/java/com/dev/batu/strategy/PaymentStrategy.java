package com.dev.batu.strategy;

import com.dev.batu.strategy.dto.AccountBalanceDto;
import com.dev.batu.strategy.dto.CommitPaymentDto;
import com.dev.batu.strategy.dto.RollbackPaymentDto;

/*
 * @created 25/05/2025 ~~ 20:46
 * author: batu   
 */
public interface PaymentStrategy {

    AccountBalanceDto commit(CommitPaymentDto dto);
    AccountBalanceDto rollback(RollbackPaymentDto dto);
}
