package com.dev.batu.strategy;

import com.dev.batu.strategy.dto.projection.AccountBalanceDto;
import com.dev.batu.strategy.dto.projection.ProcessPaymentDto;
import com.dev.batu.strategy.dto.projection.TransferTransactionDto;

import java.math.BigDecimal;

/*
 * @created 25/05/2025 ~~ 20:46
 * author: batu   
 */
public interface PaymentStrategy {

    AccountBalanceDto pay(ProcessPaymentDto dto);
}
