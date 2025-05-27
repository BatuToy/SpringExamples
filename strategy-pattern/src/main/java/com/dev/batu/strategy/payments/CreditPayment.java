package com.dev.batu.strategy.payments;

import com.dev.batu.strategy.PaymentStrategy;
import com.dev.batu.strategy.dto.projection.ProcessPaymentDto;
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
    public void pay(ProcessPaymentDto dto) {

    }

}
