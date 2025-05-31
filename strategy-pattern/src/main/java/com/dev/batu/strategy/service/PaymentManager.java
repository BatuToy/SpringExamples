package com.dev.batu.strategy.service;

/*
 * @created 25/05/2025 ~~ 21:02
 * author: batu
 */

import com.dev.batu.strategy.PaymentStrategy;
import com.dev.batu.strategy.dto.AccountBalanceDto;
import com.dev.batu.strategy.dto.CommitPaymentDto;
import com.dev.batu.strategy.dto.RollbackPaymentDto;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PaymentManager {

    /* Instead of using ApplicationContext in the class can be used Spring Auto Injection policy by
     *  inject the concrete classes in a map !
     *  */
    private final Map<String, PaymentStrategy> strategies;

    public PaymentManager(Map<String, PaymentStrategy> strategies) {
        this.strategies = strategies;
    }

    /*
    public void executePaymentWithPaymentName(String paymentType, String senderId, String receiverId, BigDecimal amount) {
        PaymentStrategy concretePayment = strategies.get(paymentType);
        Assert.notNull(concretePayment, "No payment concrete class found with the name= " + paymentType);
        //concretePayment.commit(senderId, receiverId , amount);
    }
    */

    public AccountBalanceDto commitPayment(CommitPaymentDto dto) {
        PaymentStrategy paymentStrategy = strategies.get(dto.getPaymentType());
        Assert.notNull(paymentStrategy, "No payment concrete class found for name= " + dto.getPaymentType());
        return paymentStrategy.commit(dto);
    }

    public AccountBalanceDto rollbackPayment(RollbackPaymentDto dto) {
        PaymentStrategy paymentStrategy = strategies.get(dto.getPayment().getPaymentType().name());
        Assert.notNull(paymentStrategy, " Payment strategy could not be a null value !");
        return paymentStrategy.rollback(dto);
    }
}
