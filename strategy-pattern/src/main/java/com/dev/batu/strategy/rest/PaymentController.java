package com.dev.batu.strategy.rest;

/*
 * @created 25/05/2025 ~~ 20:46
 * author: batu
 */

import com.dev.batu.strategy.dto.TransferTransactionDto;
import com.dev.batu.strategy.service.PaymentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentTransactionService paymentTransactionService;

    @GetMapping(value = "/{method}")
    public ResponseEntity<Void> triggerPaymentBeanWithStrategyClass(
            @PathVariable("method") String paymentMethod,
            @RequestParam("senderAccountId") String senderId,
            @RequestParam("receiverAccountId") String receiverId,
            @RequestParam("amount") BigDecimal amount){
        //manager.executePaymentWithPaymentName(paymentMethod, senderId, receiverId, amount);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public ResponseEntity<String> makeTransferTransaction(TransferTransactionDto transferTransactionDto){
        paymentTransactionService.commitPayment(transferTransactionDto);
        return ResponseEntity.status(HttpStatus.OK).body(" Transfer transacted without no problem ! ");
    }
}
