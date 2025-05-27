package com.dev.batu.strategy.payments;

import com.dev.batu.strategy.PaymentStrategy;
import com.dev.batu.strategy.dto.projection.AccountBalanceDto;
import com.dev.batu.strategy.dto.projection.ProcessPaymentDto;
import com.dev.batu.strategy.exception.PaymentApiException;
import com.dev.batu.strategy.model.Payment;
import com.dev.batu.strategy.model.PaymentType;
import com.dev.batu.strategy.model.Status;
import com.dev.batu.strategy.repo.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/*
 * @created 25/05/2025 ~~ 20:59
 * author: batu
 */
@Slf4j
@Component("paypal")
@RequiredArgsConstructor
public class PaypalPayment implements PaymentStrategy {

    private final PaymentRepository paymentRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public AccountBalanceDto pay(ProcessPaymentDto dto) {
        // did all validations checked ?
        // check all the flow rules ?
        if(dto.getSender().getBalance().compareTo(dto.getTransferAmount()) < 0){
            throw new PaymentApiException();
        }
        BigDecimal updatedSenderBalance = dto.getReceiver().getBalance().subtract(dto.getTransferAmount());
        BigDecimal updatedReceiverBalance = dto.getSender().getBalance().add(dto.getTransferAmount());
        paymentRepository.save(Payment.builder()
                .transactionStatus(Status.FINISHED)
                .senderAccount(dto.getSender())
                .receiverAccount(dto.getReceiver())
                .amount(dto.getTransferAmount())
                .paymentType(PaymentType.valueOf(dto.getPaymentType()))
                .build());
        return new AccountBalanceDto(updatedSenderBalance, updatedReceiverBalance);
    }

}
