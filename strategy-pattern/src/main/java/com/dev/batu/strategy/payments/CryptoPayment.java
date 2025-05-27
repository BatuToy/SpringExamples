package com.dev.batu.strategy.payments;

import com.dev.batu.strategy.PaymentStrategy;
import com.dev.batu.strategy.dto.projection.ProcessPaymentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/*
 * @created 25/05/2025 ~~ 21:01
 * author: batu
 */
@Slf4j
@Component("crypto")
public class CryptoPayment implements PaymentStrategy {

    @Override
    public void pay(ProcessPaymentDto dto) {

    }

}
