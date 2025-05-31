package com.dev.batu.strategy.dto;

/*
 * @created 30/05/2025 ~~ 21:00
 * author: batu
 */

import com.dev.batu.strategy.model.Account;
import com.dev.batu.strategy.model.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RollbackPaymentDto {

    private final Account sender;
    private final Account receiver;
    private final Payment payment;

}
