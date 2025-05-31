package com.dev.batu.strategy.dto;

/*
 * @created 30/05/2025 ~~ 19:52
 * author: batu
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReturnPaymentDto {

    private final String senderAccountNumber;
    private final String paymentId;

}
