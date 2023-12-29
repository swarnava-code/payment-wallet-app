package com.sclab.boot.paymentwalletapp.controller;

import com.sclab.boot.paymentwalletapp.entity.Transaction;
import com.sclab.boot.paymentwalletapp.service.TransactionService;
import com.sclab.boot.paymentwalletapp.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transact")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity createTransaction( @RequestParam UUID senderId,
                                             @RequestParam UUID receiverId,
                                             @RequestParam BigDecimal amount,
                                             @RequestParam(required = false) String notes) {
        if(senderId.equals(receiverId)){
            return ResponseEntity.badRequest().body(CustomResponseEntity.keyValuePairsToMap(
                    "errorMessage", String.format("both id is equal [%s]", senderId)
            ));
        }
        return transactionService.createTransaction(senderId, receiverId, amount, notes);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{transactionId}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable UUID transactionId) {
        Transaction transaction = transactionService.getTransactionById(transactionId);
        if (transaction != null) {
            return new ResponseEntity<>(transaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}