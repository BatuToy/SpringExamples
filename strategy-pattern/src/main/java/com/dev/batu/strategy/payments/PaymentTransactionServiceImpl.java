package com.dev.batu.strategy.payments;

/*
 * @created 26/05/2025 ~~ 22:26
 * author: batu
 */

import com.dev.batu.strategy.PaymentManager;
import com.dev.batu.strategy.dto.projection.AccountBalanceDto;
import com.dev.batu.strategy.dto.projection.PaymentSummaryProjection;
import com.dev.batu.strategy.dto.projection.TransferTransactionDto;
import com.dev.batu.strategy.mapper.PaymentMapper;
import com.dev.batu.strategy.model.Account;
import com.dev.batu.strategy.model.PaymentType;
import com.dev.batu.strategy.repo.AccountRepository;
import com.dev.batu.strategy.repo.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentTransactionServiceImpl implements PaymentTransactionService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final PaymentManager paymentManager;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void commitPayment(TransferTransactionDto dto) {
        Account sender = checkAccount(dto.getSenderAccountNumber());
        Account receiver = checkAccount(dto.getReceiverAccountNumber());
        AccountBalanceDto accountBalanceDto = paymentManager.executePayment(PaymentMapper.toPaymentDto(sender, receiver, dto.getTransferAmount(), dto.getPaymentType()));
        sender.setBalance(accountBalanceDto.getSenderBalance());
        receiver.setBalance(accountBalanceDto.getReceiverBalance());
        accountRepository.save(sender);
        accountRepository.save(receiver);
    }

    @Override
    public void rollbackPayment() {
        /*   go to Account service find fetch the sender and receiver account. // checkers for these two.
         *   implement the payment transaction between accounts save the accounts. // inner method.
         *   save the payment transaction entity. // inner method check for the payment transaction.
         */
    }

    @Override
    public Optional<PaymentSummaryProjection> findPaymentWithThreshold(BigDecimal threshold) {
        return paymentRepository.findPaymentWithThreshold(threshold);
    }

    private Account checkAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new EntityNotFoundException(""));
    }

}
