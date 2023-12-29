package com.sclab.boot.paymentwalletapp.service;

import com.sclab.boot.paymentwalletapp.entity.Transaction;
import com.sclab.boot.paymentwalletapp.entity.Wallet;
import com.sclab.boot.paymentwalletapp.repository.TransactionRepository;
import com.sclab.boot.paymentwalletapp.repository.WalletRepository;
import com.sclab.boot.paymentwalletapp.util.CustomResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    public ResponseEntity createTransaction(UUID senderId, UUID receiverId, BigDecimal amount, String notes) {

        Wallet sender = walletRepository.findById(senderId).orElse(null);
        Wallet receiver = walletRepository.findById(receiverId).orElse(null);

        if (sender != null && receiver != null && sender.getBalance().compareTo(amount) >= 0) {

            // Create and save the transaction
            Transaction transaction = Transaction.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .amount(amount)
                    .notes(notes)
                    .build();

            // Update sender's balance
            sender.setBalance(sender.getBalance().subtract(amount));
            walletRepository.save(sender);

            // Update receiver's balance
            receiver.setBalance(receiver.getBalance().add(amount));
            walletRepository.save(receiver);

            return new ResponseEntity<>(transactionRepository.save(transaction), HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body(CustomResponseEntity.keyValuePairsToMap(
                    "errorMessage", "user unavailable or user's balance not satisfied"
            ));
        }
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(UUID transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }

}