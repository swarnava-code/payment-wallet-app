package com.sclab.boot.paymentwalletapp.service;

import com.sclab.boot.paymentwalletapp.advice.TransactionFailedException;
import com.sclab.boot.paymentwalletapp.entity.Transaction;
import com.sclab.boot.paymentwalletapp.entity.Wallet;
import com.sclab.boot.paymentwalletapp.enumeration.TransactionStatus;
import com.sclab.boot.paymentwalletapp.repository.TransactionRepository;
import com.sclab.boot.paymentwalletapp.repository.WalletRepository;
import com.sclab.boot.paymentwalletapp.util.TimeUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private KafkaProducerService kafkaProducerService;

    public Transaction createTransaction(UUID senderId, UUID receiverId, BigDecimal amount, String notes) {
        Wallet sender = walletRepository.findById(senderId).orElse(null);
        Wallet receiver = walletRepository.findById(receiverId).orElse(null);
        // Create and save the transaction
        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(amount)
                .notes(notes)
                .status(TransactionStatus.FAILURE)
                .build();
        if (sender == null) {
            throw new TransactionFailedException("sender not exist", transactionRepository.save(transaction));
        }
        if (receiver == null) {
            throw new TransactionFailedException("receiver not exist", transactionRepository.save(transaction));
        }
        if (sender.getBalance().compareTo(amount) <= 0) {
            throw new TransactionFailedException("sender have insufficient balance",
                    transactionRepository.save(transaction));
        }
        if (sender.getLimitPerTransaction() < amount.intValue()) {
            throw new TransactionFailedException("It shouldn't cross 'LimitPerTransaction' value.",
                    transactionRepository.save(transaction));
        }
        BigDecimal sumOfAllTransactionInLast24Hours =
                transactionRepository.findSumOfAllTransactionInLast24Hours(senderId.toString(),
                        TimeUtil.currentDate().toString());
        int sumOfAllTransaction = (sumOfAllTransactionInLast24Hours == null) ? 0
                : sumOfAllTransactionInLast24Hours.intValue();
        int limitTotalAmountPerDay = sender.getLimitTotalAmountPerDay();
        if (limitTotalAmountPerDay < (sumOfAllTransaction + amount.intValue())) {
            throw new TransactionFailedException(
                    String.format("transaction failed: You shouldn't cross the value of 'limitTotalAmountPerDay'" +
                            " \nYou can pay maximum %s %s for today till 11:59 PM",
                    (limitTotalAmountPerDay - sumOfAllTransaction), sender.getCurrencyCode()),
                    transactionRepository.save(transaction));
        }
        transaction.setStatus(TransactionStatus.SUCCESS);
        Transaction savedTransaction = saveRequiredDataIntoAllDb(sender, receiver, amount, transaction);
        // send notification
        try {
            kafkaProducerService.sendTransactionNotification(transaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return savedTransaction;
    }

    @Transactional
    private Transaction saveRequiredDataIntoAllDb(Wallet sender, Wallet receiver, BigDecimal amount,
                                                  Transaction transaction) {
        // Update sender's balance : Deduct money from Sender’s Wallet
        sender.setBalance(sender.getBalance().subtract(amount));
        walletRepository.save(sender);
        // Update receiver's balance : Credit money to the Receiver's Wallet
        receiver.setBalance(receiver.getBalance().add(amount));
        walletRepository.save(receiver);
        // Add Transaction data
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(UUID transactionId) {
        return transactionRepository.findById(transactionId).orElse(null);
    }

    public List<Transaction> getAllTransactionBySenderId(UUID senderId) {
        return transactionRepository.findTransactionBySenderId(senderId);
    }

}