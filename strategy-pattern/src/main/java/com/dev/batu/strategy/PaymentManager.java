package com.dev.batu.strategy;

/*
 * @created 25/05/2025 ~~ 21:02
 * author: batu
 */

import com.dev.batu.strategy.dto.projection.AccountBalanceDto;
import com.dev.batu.strategy.dto.projection.ProcessPaymentDto;
import com.dev.batu.strategy.dto.projection.TransferTransactionDto;
import com.dev.batu.strategy.model.Payment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class PaymentManager {

    /* Instead of using ApplicationContext in the class can be used Spring Auto Injection policy by
    *  inject the concrete classes in a map !
    *  */
    private final Map<String , PaymentStrategy> strategies;

    public PaymentManager(Map<String, PaymentStrategy> strategies) {
        this.strategies = strategies;
    }

    public void executePaymentWithPaymentName(String paymentType, String senderId, String receiverId, BigDecimal amount){
        PaymentStrategy concretePayment = strategies.get(paymentType);
        Assert.notNull(concretePayment, "No payment concrete class found with the name= " + paymentType);
        //concretePayment.pay(senderId, receiverId , amount);
    }

    public AccountBalanceDto executePayment(ProcessPaymentDto dto){
        PaymentStrategy paymentStrategy = strategies.get(dto.getPaymentType());
        Assert.notNull(paymentStrategy, "No payment concrete class found with the name= " + dto.getPaymentType());
        return paymentStrategy.pay(dto);
    }
}
