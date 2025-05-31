package com.dev.batu.strategy.concrete_payments;

import com.dev.batu.strategy.PaymentStrategy;
import com.dev.batu.strategy.dto.AccountBalanceDto;
import com.dev.batu.strategy.dto.CommitPaymentDto;
import com.dev.batu.strategy.dto.RollbackPaymentDto;
import com.dev.batu.strategy.exception.PaymentApiException;
import com.dev.batu.strategy.model.Account;
import com.dev.batu.strategy.model.Payment;
import com.dev.batu.strategy.model.PaymentType;
import com.dev.batu.strategy.model.Status;
import com.dev.batu.strategy.service.PaymentTransactionServiceImpl;
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
    private final PaymentTransactionServiceImpl paymentTransactionServiceImpl;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public AccountBalanceDto commit(CommitPaymentDto dto) {
        validatePaymentForCommit(dto.getSender(), dto.getPaymentType(), dto.getTransferAmount());
        BigDecimal updatedSenderBalance = dto.getSender().getBalance().subtract(dto.getTransferAmount());
        BigDecimal updatedReceiverBalance = dto.getReceiver().getBalance().add(dto.getTransferAmount());
        paymentRepository.saveAndFlush(Payment.builder()
                .transactionStatus(Status.COMMIT)
                .senderAccount(dto.getSender())
                .receiverAccount(dto.getReceiver())
                .amount(dto.getTransferAmount())
                .paymentType(PaymentType.valueOf(dto.getPaymentType()))
                .build());
        return new AccountBalanceDto(updatedSenderBalance, updatedReceiverBalance);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public AccountBalanceDto rollback(RollbackPaymentDto dto) {
        Account sender = dto.getSender();
        Account receiver = dto.getReceiver();
        Payment payment = dto.getPayment();
        BigDecimal transferAmount = payment.getAmount();
        validatePaymentForRollBack(payment, sender);
        BigDecimal senderBalance = sender.getBalance().subtract(transferAmount);
        BigDecimal receiverBalance = receiver.getBalance().add(transferAmount);
        Payment persistedPayment = paymentRepository.saveAndFlush(Payment.builder()
                        .payment(payment)
                        .transactionStatus(Status.RETURNED)
                        .senderAccount(sender)
                        .receiverAccount(receiver)
                        .amount(transferAmount)
                        .paymentType(payment.getPaymentType())
                .build());
        return new AccountBalanceDto(senderBalance, receiverBalance, persistedPayment);
    }

    private void validatePaymentForCommit(Account sender, String paymentType, BigDecimal transferAmount){
        if(sender.getBalance().compareTo(transferAmount) < 0){
            throw new PaymentApiException("");
        }
    }

    private void validatePaymentForRollBack(Payment payment, Account sender) {
        if(payment.getTransactionStatus().equals(Status.FINISHED) ||
                payment.getTransactionStatus().equals(Status.RETURNED)) {
            log.error("Payment is not in the correct state for returning the payment !");
            throw new PaymentApiException("Payment is not in the correct state for returning the payment !");
        } else if(sender.getBalance().compareTo(payment.getAmount()) < 0) {
            log.error("Sender with account number = {} does not have enough balance to return the payment !",
                    sender.getAccountNumber());
            throw new PaymentApiException("Sender with account number = " + sender.getAccountNumber()
                + " does not have enough balance to return the payment !");
        }
    }

}
