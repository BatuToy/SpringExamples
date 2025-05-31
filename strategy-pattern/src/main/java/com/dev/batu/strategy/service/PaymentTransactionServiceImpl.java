package com.dev.batu.strategy.service;

/*
 * @created 26/05/2025 ~~ 22:26
 * author: batu
 */

import com.dev.batu.strategy.dto.AccountBalanceDto;
import com.dev.batu.strategy.dto.projections.PaymentSummaryProjection;
import com.dev.batu.strategy.dto.ReturnPaymentDto;
import com.dev.batu.strategy.dto.TransferTransactionDto;
import com.dev.batu.strategy.exception.PaymentApiException;
import com.dev.batu.strategy.mapper.PaymentMapper;
import com.dev.batu.strategy.model.Account;
import com.dev.batu.strategy.model.Payment;
import com.dev.batu.strategy.model.PaymentType;
import com.dev.batu.strategy.model.Status;
import com.dev.batu.strategy.repo.AccountRepository;
import com.dev.batu.strategy.repo.PaymentRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final PaymentManager paymentManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, noRollbackFor = OptimisticLockException.class)
    public void commitPayment(TransferTransactionDto dto) {
        Account sender = checkAccount(dto.getSenderAccountNumber());
        Account receiver = checkAccount(dto.getReceiverAccountNumber());
        try {
            AccountBalanceDto accountBalanceDto = paymentManager
                    .commitPayment(PaymentMapper.toCommitPaymentDto(sender,
                            receiver,
                            dto.getTransferAmount(),
                            dto.getPaymentType()));
            sender.setBalance(accountBalanceDto.getSenderBalance());
            receiver.setBalance(accountBalanceDto.getReceiverBalance());
            accountRepository.save(sender);
            accountRepository.save(receiver);
        } catch (OptimisticLockException optimisticLockException) {
            paymentRepository.saveAndFlush(Payment.builder()
                    .transactionStatus(Status.FAILED)
                    .paymentType(PaymentType.valueOf(dto.getPaymentType()))
                    .amount(dto.getTransferAmount())
                    .senderAccount(sender)
                    .receiverAccount(receiver)
                    .build());
            log.error(optimisticLockException.getMessage(), optimisticLockException);
            throw new OptimisticLockException(optimisticLockException.getMessage(), optimisticLockException);
            // let's assume kafka in the play ! KAFKA -> failure payment sent to Order kinda ms.
        }

    }

    @Override
    public void returnPayment(ReturnPaymentDto dto) {
        Payment payment = checkPayment(dto.getPaymentId());
        // Initial sender is in this payment transaction belongs to previous payment receiver !
        Account sender = payment.getReceiverAccount();
        Account receiver = payment.getSenderAccount();
        try {
            AccountBalanceDto accountBalanceDto = paymentManager.rollbackPayment(PaymentMapper.toRollbackPaymentDto(
                    sender,
                    receiver,
                    payment
            ));
            sender.setBalance(accountBalanceDto.getSenderBalance());
            receiver.setBalance(accountBalanceDto.getReceiverBalance());
            accountRepository.save(sender);
            accountRepository.save(receiver);
        } catch (OptimisticLockException optimisticLockException) {
            processFailedPayment(payment, sender, receiver);
            log.error(optimisticLockException.getMessage(), optimisticLockException);
            throw new OptimisticLockException(optimisticLockException.getMessage(), optimisticLockException);
        }
    }

    private Account checkAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new PaymentApiException("Account with account number = " + accountNumber + " did not found in the persist store !"));
    }

    private Payment checkPayment(String id) {
        return paymentRepository.fetchPaymentById(UUID.fromString(id))
                .orElseThrow( () -> new PaymentApiException("Payment with id = " + id + " not found in the persist store !"));
    }

    private void processFailedPayment(Payment payment, Account sender, Account receiver){
        paymentRepository.saveAndFlush(Payment.builder()
                        .transactionStatus(Status.FAILED)
                        .payment(payment)
                        .paymentType(payment.getPaymentType())
                        .senderAccount(sender)
                        .receiverAccount(receiver)
                        .amount(payment.getAmount())
                .build());
    }

    /*
    private boolean hasAnyPreviousPaymentTransaction(Payment payment) {
        return !Objects.isNull(payment.getPayment());
    }
     */

    // DASHBOARD METHODS

    @Override
    public Optional<PaymentSummaryProjection> findPaymentWithThreshold(BigDecimal threshold) {
        return paymentRepository.findPaymentWithThreshold(threshold);
    }
}
